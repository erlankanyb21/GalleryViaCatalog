package com.example.galleryviacatalog.ui.notifications

import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.galleryviacatalog.R
import com.example.galleryviacatalog.databinding.FragmentCreateCatalogBinding
import com.example.galleryviacatalog.ui.home.base.BaseFragment

class CreateCatalogFragment :
    BaseFragment<FragmentCreateCatalogBinding, NotificationsViewModel>(R.layout.fragment_create_catalog) {
    override val binding by viewBinding(FragmentCreateCatalogBinding::bind)
    override val viewModel by viewModels<NotificationsViewModel>()

    override fun constructListeners() {
        binding.btnCreate.setOnClickListener {
            viewModel.createCatalog(255, binding.etCatalogName.text.toString())
                .observe(viewLifecycleOwner) {
                    Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
                    binding.tvCatalogName.isVisible = true
                    binding.tvCatalogName.text = it.name

                    binding.etCatalogName.isVisible = false
                    binding.btnCreate.isVisible = false

                    binding.emptyLayout.isVisible = true

                }
        }

        binding.ivCirclePlusBtn.setOnClickListener {
            findNavController().navigate(R.id.navigation_notifications)
        }

        binding.etCatalogName.addTextChangedListener { text->
            if (text.toString().isNotEmpty()){
                binding.btnCreate.backgroundTintList = ColorStateList.valueOf(Color.BLUE)
                binding.btnCreate.isEnabled = true
            } else {
                binding.btnCreate.backgroundTintList = ColorStateList.valueOf(Color.DKGRAY)
                binding.btnCreate.isEnabled = false
            }
        }
    }

}