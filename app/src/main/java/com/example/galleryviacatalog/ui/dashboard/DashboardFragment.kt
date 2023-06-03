package com.example.galleryviacatalog.ui.dashboard

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.galleryviacatalog.R
import com.example.galleryviacatalog.databinding.FragmentDashboardBinding
import com.example.galleryviacatalog.ui.home.HomeViewModel
import com.example.galleryviacatalog.ui.home.base.BaseFragment

class DashboardFragment : BaseFragment<FragmentDashboardBinding,HomeViewModel>(R.layout.fragment_dashboard) {

    override val binding by viewBinding(FragmentDashboardBinding::bind)
    override val viewModel by viewModels<HomeViewModel>()
    private val args by navArgs<DashboardFragmentArgs>()

    override fun initialize() {
        showUpPhoto()
    }

    private fun showUpPhoto() {
        binding.imageCharactersPhotoDetail.load(args.photo) {
            listener { request, result ->
                binding.progress.isVisible = false
            }
        }
    }
}