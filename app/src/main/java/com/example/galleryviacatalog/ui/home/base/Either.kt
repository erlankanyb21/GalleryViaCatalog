package com.example.galleryviacatalog.ui.home.base

/**
 * `Sealed class`, который может содержать значение типа [A] - Ошибка или [Nothing] или типа [B] - Данные или [Nothing].
 *
 * @param A тип первого значения - Ошибка или [Nothing].
 * @param B тип второго значения - Данные или [Nothing].
 */
sealed class Either<out A, out B> {
    /**
     * Подкласс [Left] для получения ошибок.
     *
     * @param value значение типа [A] - Ошибка или [Nothing].
     */
    class Left<out A>(val value: A) : Either<A, Nothing>()

    /**
     * Подкласс [Right] для получения данных.
     *
     * @param value значение типа [B] - Данные или Nothing.
     */
    class Right<out B>(val value: B) : Either<Nothing, B>()
}