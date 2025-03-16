package com.firooze.data

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .also {
                it.header("Authorization", "1312d57c557e4bc095ef56ef663168ad")
            }
            .build()

        return chain.proceed(request)
    }
}