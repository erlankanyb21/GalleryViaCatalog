package com.example.galleryviacatalog.ui.home.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    @Suppress("FunctionName")
    protected fun <T> MutableUIStateFlow() = MutableStateFlow<NewUIState<T>>(NewUIState.Idle())

    protected fun <T> MutableStateFlow<NewUIState<T>>.reset() {
        value = NewUIState.Idle()
    }

    protected fun <T> Flow<Either<NetworkError, T>>.gatherNetworkRequest(
        state: MutableStateFlow<NewUIState<T>>
    ) = gatherRequest(state) {
        NewUIState.Success(it)
    }

    protected fun <T, S> Flow<Either<NetworkError, T>>.gatherNetworkRequest(
        state: MutableStateFlow<NewUIState<S>>,
        mapToUI: (T) -> S
    ) = gatherRequest(state) {
        NewUIState.Success(mapToUI(it))
    }

    private fun <T, S> Flow<Either<NetworkError, T>>.gatherRequest(
        state: MutableStateFlow<NewUIState<S>>,
        successful: (T) -> NewUIState.Success<S>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            state.value = NewUIState.Loading()
            this@gatherRequest.collect {
                when (it) {
                    is Either.Left -> state.value = NewUIState.Error(it.value)
                    is Either.Right -> state.value = successful(it.value)
                }
            }
        }
    }

    protected fun <T, S> Flow<T>.gatherLocalRequest(
        mapToUI: (T) -> S
    ): Flow<S> = map { value: T ->
        mapToUI(value)
    }

    protected fun <T, S> Flow<List<T>>.gatherLocalRequestForList(
        mapToUI: (T) -> S
    ): Flow<List<S>> = map { value: List<T> ->
        value.map { data: T ->
            mapToUI(data)
        }
    }

}