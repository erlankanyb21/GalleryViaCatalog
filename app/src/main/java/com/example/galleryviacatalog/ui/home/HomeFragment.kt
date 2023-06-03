package com.example.galleryviacatalog.ui.home

import android.widget.Toast
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
        setUpRecycler()
    }

    private fun setUpRecycler() {
        galleryPhotoAdapter = GalleryPhotoAdapter(this::onItemClick, this::onItemLongClick)
        binding.galleryRecycler.adapter = galleryPhotoAdapter
    }

    private fun onItemClick(photoId: Int) {
        Toast.makeText(requireContext(), "$photoId", Toast.LENGTH_SHORT).show()
    }

    private fun onItemLongClick(selectedItems: List<Int>) {
        binding.deleteItem.setOnClickListener {
            // Обработка удаления выбранных элементов
            selectedItems.forEach { id ->

            }
            Toast.makeText(requireContext(), "$selectedItems", Toast.LENGTH_SHORT).show()
        }
    }

    override fun constructListeners() {
        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.navigation_notifications)
        }
        binding.ivDelete.setOnClickListener {
            galleryPhotoAdapter.selectAllItems()
        }
    }

    override fun launchObservers() {
        viewModel.getStats().observe(viewLifecycleOwner) {
            galleryPhotoAdapter.submitList(it.banners.toMutableList())
        }
    }
}