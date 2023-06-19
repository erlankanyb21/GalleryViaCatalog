package com.example.galleryviacatalog.ui.notifications

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.galleryviacatalog.R
import com.example.galleryviacatalog.databinding.FragmentNotificationsBinding
import com.example.galleryviacatalog.ui.home.base.BaseFragment

class NotificationsFragment : BaseFragment<FragmentNotificationsBinding,NotificationsViewModel>(R.layout.fragment_notifications) {
    override val binding by viewBinding(FragmentNotificationsBinding::bind)
    override val viewModel by viewModels<NotificationsViewModel>()
    private val catalogAdapter by lazy {
        CatalogAdapter(this::onItemClick,this::onItemLongClick)
    }

    private fun onItemLongClick(selectedItems: List<Int>) {

    }

    private fun onItemClick(uri: String, id: Int) {

    }

    override fun initialize() {
        binding.rvCatalog.adapter = catalogAdapter
    }

    override fun constructListeners() {

    }

    override fun launchObservers() {
        viewModel.getProducts().observe(viewLifecycleOwner) {
            catalogAdapter.submitList(it.results)
        }
    }

}