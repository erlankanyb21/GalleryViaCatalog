package com.example.galleryviacatalog.ui.home

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.galleryviacatalog.R
import com.example.galleryviacatalog.databinding.FragmentHomeBinding
import com.example.galleryviacatalog.ui.home.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {

    override val binding by viewBinding(FragmentHomeBinding::bind)
    override val viewModel by viewModels<HomeViewModel>()

    private lateinit var galleryPhotoAdapter: GalleryPhotoAdapter

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
            val alertDialog = AlertDialog.Builder(requireContext())
                .setTitle("Вы действительно хотите удалить выбранные элементы?")
                .setNegativeButton("Отменить") { dialog, _ ->
                    dialog.dismiss()
                }.setPositiveButton("Удалить") { dialog, _ ->
                    selectedItems.forEach { id ->
                        viewModel.delete(id)
                    }
                    Toast.makeText(requireContext(), "$selectedItems Deleted", Toast.LENGTH_SHORT)
                        .show()
                    dialog.dismiss()
                }.create()

            alertDialog.show()
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
            Toast.makeText(requireContext(), "$id", Toast.LENGTH_SHORT).show()
        }
    }

    override fun launchObservers() {
        viewModel.getStats().observe(viewLifecycleOwner) {
            if (it.banners.isEmpty()) {
                binding.emptyLayout.visibility = View.VISIBLE
            } else {
                galleryPhotoAdapter.submitList(it.banners.toMutableList())
                binding.ivCirclePlusBtn.visibility = View.GONE
            }
        }
    }
}
