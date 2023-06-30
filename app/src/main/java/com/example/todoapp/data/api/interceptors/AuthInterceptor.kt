package com.example.todoapp.data.api.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        requestBuilder.addHeader("Authorization", "Bearer prulaurasin")

        return chain.proceed(requestBuilder.build())
    }
}