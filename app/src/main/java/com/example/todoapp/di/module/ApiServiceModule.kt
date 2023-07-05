package com.example.todoapp.di.module

import com.example.todoapp.data.api.ApiService
import com.example.todoapp.data.api.interceptors.AuthInterceptor
import com.example.todoapp.data.api.interceptors.RetryInterceptor
import com.example.todoapp.di.scope.AppScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
interface ApiServiceModule {
    companion object {
        @AppScope
        @Provides
        fun provideApiService(
            okHttpClient: OkHttpClient,
            gsonConverterFactory: GsonConverterFactory
        ): ApiService {
            return Retrofit.Builder()
                .baseUrl("https://beta.mrdekk.ru/todobackend/")
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .build()
                .create(ApiService::class.java)
        }

        @AppScope
        @Provides
        fun provideGsonConverterFactory(): GsonConverterFactory {
            return GsonConverterFactory.create()
        }

        @AppScope
        @Provides
        fun provideOkHttpClient(
            loggingInterceptor: HttpLoggingInterceptor,
            authInterceptor: AuthInterceptor,
            retryInterceptor: RetryInterceptor
        ): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .addInterceptor(retryInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .build()
        }

        @AppScope
        @Provides
        fun provideRetryInterceptor(): RetryInterceptor {
            return RetryInterceptor()
        }

        @AppScope
        @Provides
        fun provideAuthInterceptor(): AuthInterceptor {
            return AuthInterceptor()
        }

        @AppScope
        @Provides
        fun provideLoggingInterceptor(): HttpLoggingInterceptor {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            return logging
        }
    }
}