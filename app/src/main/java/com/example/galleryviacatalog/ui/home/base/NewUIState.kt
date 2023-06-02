package com.example.galleryviacatalog.ui.home.base

/**
 * `Sealed class`, представляющий состояния `UI` для выполнения различных операций.
 *
 * @param T тип данных, связанных с состоянием.
 */
sealed class NewUIState<T> {

    class Idle<T> : NewUIState<T>()

    /**
     * Состояние загрузки.
     */
    class Loading<T> : NewUIState<T>()

    /**
     * Состояние ошибки.
     *
     * @property error сообщение об ошибке.
     */
    class Error<T>(val error: NetworkError) : NewUIState<T>()

    /**
     * Состояние успешного получения данных.
     *
     * @property data Данные, которые были получены.
     */
    class Success<T>(val data: T) : NewUIState<T>()
}