package com.example.todoapp.data.api.interceptors

import android.util.Log
import com.example.todoapp.di.scope.AppScope
import com.example.todoapp.utils.RETRY_COUNT
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.HttpException

@AppScope
class RetryInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response: Response? = null
        var responseOK = false
        var tryCount = 0

        while (!responseOK && tryCount < RETRY_COUNT) {
            try {
                response = chain.proceed(request)
                responseOK = response.isSuccessful
            } catch (e: HttpException) {
                Log.d("intercept", "HTTP error occurred - $tryCount: ${e.code()}")
            } finally {
                tryCount++
            }
        }

        return response!!
    }
}
