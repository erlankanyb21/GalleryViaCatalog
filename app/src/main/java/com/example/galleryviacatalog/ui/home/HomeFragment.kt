package com.example.galleryviacatalog.ui.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.galleryviacatalog.R
import com.example.galleryviacatalog.databinding.FragmentHomeBinding
import com.example.galleryviacatalog.ui.home.base.BaseFragment
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val binding by viewBinding(FragmentHomeBinding::bind)
    override val viewModel by viewModels<HomeViewModel>()

    private lateinit var galleryPhotoAdapter: GalleryPhotoAdapter

    private companion object {
        const val REQUEST_IMAGE_GALLERY = 1
        const val REQUEST_READ_EXTERNAL_STORAGE = 2
    }

    override fun initialize() {
        viewModel.getStats()
        setUpRecycler()
    }

    private fun setUpRecycler() {
        galleryPhotoAdapter = GalleryPhotoAdapter(this::onItemClick, this::onItemLongClick)
        binding.galleryRecycler.adapter = galleryPhotoAdapter
    }

    private fun onItemClick(photoId: String) {
        findNavController().navigate(
            HomeFragmentDirections.actionNavigationHomeToNavigationDashboard(
                photo = photoId
            )
        )
    }

    private fun onItemLongClick(selectedItems: List<Int>) {
        binding.deleteItem.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Вы действительно хотите удалить выбранные элементы?")
                .setNegativeButton("Отменить") { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton("Удалить") { dialog, _ ->
                    selectedItems.forEach { id ->
                        viewModel.delete(id)
                    }
                    Toast.makeText(requireContext(), "$selectedItems Deleted", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }

    override fun constructListeners() {
        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.navigation_notifications)
        }
        binding.ivDelete.setOnClickListener {
            galleryPhotoAdapter.selectAllItems()
        }
        binding.ivCirclePlusBtn.setOnClickListener {
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
                requireActivity(),
                arrayOf(permission),
                REQUEST_READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun startImageGalleryActivity() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
        }
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            if (selectedImageUri != null) {
                val imagePath = getImagePath(selectedImageUri)
                val imageBytes = getImageBytes(imagePath)
                viewModel.addGalleryPhoto(imageBytes).observe(viewLifecycleOwner) { photoResponse ->
                    Toast.makeText(requireContext(), "${photoResponse.photo}", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(requireContext(), "Не удалось получить изображение", Toast.LENGTH_SHORT).show()
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Разрешение на чтение внешнего хранилища было предоставлено
                startImageGalleryActivity()
            } else {
                Toast.makeText(requireContext(), "Не удалось получить разрешение на чтение внешнего хранилища", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun launchObservers() {
        viewModel.getStats().observe(viewLifecycleOwner) { it ->
            if (it.banners.isEmpty()) {
                binding.emptyLayout.visibility = View.VISIBLE
            } else {
                galleryPhotoAdapter.submitList(it.banners.toMutableList())
                binding.ivCirclePlusBtn.visibility = View.GONE
            }
        }
    }
}