package com.example.galleryviacatalog.ui.dashboard

import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.example.galleryviacatalog.R
import com.example.galleryviacatalog.databinding.FragmentDashboardBinding
import com.example.galleryviacatalog.ui.home.HomeViewModel
import com.example.galleryviacatalog.ui.home.base.BaseFragment

class DashboardFragment :
    BaseFragment<FragmentDashboardBinding, HomeViewModel>(R.layout.fragment_dashboard) {

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

    override fun constructListeners() {
        binding.ivDelete.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Вы действительно хотите удалить выбранные элементы?")
                .setNegativeButton("Отменить") { dialog, _ ->
                    dialog.dismiss()
                }.setPositiveButton("Удалить") { dialog, _ ->
                    viewModel.delete(id)
                    findNavController().navigateUp()
                    Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }.create().show()

        }
    }
}