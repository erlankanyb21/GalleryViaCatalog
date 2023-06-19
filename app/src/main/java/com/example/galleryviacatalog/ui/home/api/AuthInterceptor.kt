package com.example.galleryviacatalog.ui.home.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
) : Interceptor {

    /**
     * Метод перехватывает исходящие HTTP-запросы и добавляет заголовок "Authorization" с токеном доступа.
     *
     * @param chain объект [Interceptor.Chain], предоставляющий доступ к информации о запросе и ответе.
     * @return объект Response с результатами выполнения запроса.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (request.header("Authorization").isNullOrEmpty()) {
            request = chain.request().newBuilder()
                .header(
                    "Authorization",
                    "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNjg3MTcyOTA5LCJqdGkiOiI0N2EyMDA2YzI0N2M0YWZmOThlNjVlYzZkNDZjOWIwYyIsInVzZXJfaWQiOjEyMjMwfQ.lA3vIsiqZnNOaX33DRiRbKWrrnUhBOYk361oIBYHPCk"
                )
                .build()
        }
        return chain.proceed(request)
    }
}