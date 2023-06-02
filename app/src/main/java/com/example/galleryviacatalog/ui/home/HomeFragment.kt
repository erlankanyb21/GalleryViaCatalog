package com.example.galleryviacatalog.ui.home

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.galleryviacatalog.R
import com.example.galleryviacatalog.databinding.FragmentHomeBinding
import com.example.galleryviacatalog.ui.home.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding,HomeViewModel>(R.layout.fragment_home) {

    override val binding by viewBinding(FragmentHomeBinding::bind)

    override val viewModel by viewModels<HomeViewModel>()

    private val galleryPhotoAdapter by lazy { GalleryPhotoAdapter(this::onItemClick, this::onItemLongClick) }

    private fun onItemClick(ints: List<Int>) {
        Toast.makeText(requireContext(), "$ints", Toast.LENGTH_SHORT).show()
    }

    private fun onItemLongClick(s: Int) {}

    override fun initialize() {
        setUpRecycler()
    }

    private fun setUpRecycler() {
        binding.galleryRecycler.adapter = galleryPhotoAdapter
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