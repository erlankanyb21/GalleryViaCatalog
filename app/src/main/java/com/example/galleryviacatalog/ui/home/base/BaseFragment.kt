package com.example.galleryviacatalog.ui.home.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseFragment<Binding : ViewBinding, ViewModel : BaseViewModel>(
    @LayoutRes layoutId: Int
) :
    Fragment(layoutId) {
    protected abstract val binding: Binding
    protected abstract val viewModel: ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        assembleViews()
        constructListeners()
        establishRequest()
        launchObservers()
    }

    protected open fun initialize() {}

    protected open fun assembleViews() {}

    protected open fun constructListeners() {}

    protected open fun establishRequest() {}

    protected open fun launchObservers() {}


    protected fun <T> StateFlow<NewUIState<T>>.spectateUiState(
        lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
        state: ((NewUIState<T>) -> Unit)? = null,
        onError: ((error: NetworkError) -> Unit),
        onSuccess: ((data: T) -> Unit)
    ) {
        safeFlowGather(lifecycleState) {
            this@spectateUiState.collect {
                state?.invoke(it)
                when (it) {
                    is NewUIState.Idle -> {}
                    is NewUIState.Loading -> {}
                    is NewUIState.Error -> onError.invoke(it.error)
                    is NewUIState.Success -> onSuccess.invoke(it.data)
                }
            }
        }
    }

    fun safeFlowGather(
        lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
        gather: suspend () -> Unit,
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(lifecycleState) {
                gather()
            }
        }
    }
}