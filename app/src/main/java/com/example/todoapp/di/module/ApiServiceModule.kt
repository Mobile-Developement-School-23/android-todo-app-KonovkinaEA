package com.example.todoapp.di.module

import com.example.todoapp.data.api.ApiService
import com.example.todoapp.data.api.interceptors.AuthInterceptor
import com.example.todoapp.data.api.interceptors.RetryInterceptor
import com.example.todoapp.di.qualifier.ApiQualifier
import com.example.todoapp.di.scope.AppScope
import com.example.todoapp.utils.CONNECT_TIMEOUT
import com.example.todoapp.utils.READ_TIMEOUT
import com.example.todoapp.utils.WRITE_TIMEOUT
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
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build()
        }

        @AppScope
        @Provides
        fun provideRetryInterceptor(): RetryInterceptor {
            return RetryInterceptor()
        }

        @AppScope
        @Provides
        fun provideAuthInterceptor(@ApiQualifier token: String): AuthInterceptor {
            return AuthInterceptor(token)
        }

        @AppScope
        @Provides
        fun provideLoggingInterceptor(): HttpLoggingInterceptor {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            return logging
        }

        @AppScope
        @ApiQualifier
        @Provides
        fun provideToken(): String {
            return "Bearer prulaurasin"
        }
    }
}
