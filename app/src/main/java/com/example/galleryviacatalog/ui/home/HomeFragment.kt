package com.example.galleryviacatalog.ui.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.galleryviacatalog.R
import com.example.galleryviacatalog.databinding.FragmentHomeBinding
import com.example.galleryviacatalog.ui.home.base.BaseFragment
import java.io.File

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val binding by viewBinding(FragmentHomeBinding::bind)

    override val viewModel by viewModels<HomeViewModel>()

    private var galleryPhotoAdapter: GalleryPhotoAdapter? = null

    private companion object {
        const val REQUEST_READ_EXTERNAL_STORAGE = 2
    }

    private lateinit var galleryActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun initialize() {
        setUpRecycler()
        constructListeners()

        viewModel.getStats().observe(viewLifecycleOwner) {
            if (it.banners.isEmpty()) {
                binding.emptyLayout.visibility = View.VISIBLE
            } else {
                galleryPhotoAdapter?.submitList(it.banners.toMutableList())
                binding.ivCirclePlusBtn.visibility = View.GONE
            }
        }
    }

    private fun setUpRecycler() {
        galleryPhotoAdapter = GalleryPhotoAdapter(this::onItemClick, this::onItemLongClick)
        binding.galleryRecycler.adapter = galleryPhotoAdapter
    }

    private fun onItemClick(photoId: String, id: Int) {
        findNavController().navigate(
            HomeFragmentDirections.actionNavigationHomeToNavigationDashboard(
                photo = photoId,
                id = id
            )
        )
    }

    private fun onItemLongClick(selectedItems: List<Int>) = with(binding) {

        when (selectedItems.isEmpty()) {
            true -> {
                ivDelete.isVisible = false
                ivAdd.isVisible = true
                deleteItem.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#4A637C"))
                deleteItem.isEnabled = false
            }

            false -> {
                ivDelete.isVisible = true
                ivAdd.isVisible = false
                deleteItem.backgroundTintList = ColorStateList.valueOf(Color.BLUE)
                deleteItem.isEnabled = true
            }
        }
        deleteItem.setOnClickListener {
            if (selectedItems.isNotEmpty()) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Вы действительно хотите удалить выбранные элементы?")
                    .setNegativeButton("Отменить") { dialog, _ ->
                        dialog.dismiss()
                    }.setPositiveButton("Удалить") { dialog, _ ->
                        selectedItems.forEach { id ->
                            viewModel.delete(id)
                        }
                        Toast.makeText(
                            requireContext(),
                            "$selectedItems Deleted",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        dialog.dismiss()
                    }.create().show()
            }
        }
    }

    override fun constructListeners() {
        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.createCatalogFragment)
        }
        binding.ivDelete.setOnClickListener {
            galleryPhotoAdapter?.selectAllItems()
        }

        binding.ivCirclePlusBtn.setOnClickListener {
            openImageGallery()
        }

        binding.ivAdd.setOnClickListener {
            openImageGallery()
        }
    }

    private fun openImageGallery() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        val grant = PackageManager.PERMISSION_GRANTED
        if (ContextCompat.checkSelfPermission(requireContext(), permission) == grant) {
            // У приложения уже есть разрешение на чтение внешнего хранилища
            startImageGalleryActivity()
        } else {
            // Запросить разрешение на чтение внешнего хранилища
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(permission), REQUEST_READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun startImageGalleryActivity() {
        val intent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                type = "image/*"
            }
        galleryActivityResultLauncher.launch(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize galleryActivityResultLauncher
        galleryActivityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                    val selectedImageUri = result.data?.data
                    selectedImageUri?.let { uri ->
                        val imagePath = getImagePath(uri)
                        val imageBytes = getImageBytes(imagePath)
                        viewModel.addGalleryPhoto(imageBytes)
                            .observe(viewLifecycleOwner) { photoResponse ->
                                Toast.makeText(
                                    requireContext(), photoResponse.photo, Toast.LENGTH_LONG
                                ).show()
                            }
                    } ?: Toast.makeText(
                        requireContext(), "Не удалось получить изображение", Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun getImagePath(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().contentResolver.query(uri, projection, null, null, null)
        cursor?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            return it.getString(columnIndex)
        }
        return ""
    }

    private fun getImageBytes(imagePath: String): ByteArray {
        val imageFile = File(imagePath)
        return imageFile.readBytes()
    }
}
