package com.example.todoapp.di.module

import android.content.Context
import android.net.ConnectivityManager
import com.example.todoapp.di.scope.AppScope
import dagger.Module
import dagger.Provides

@Module
interface WorkerModule {
    companion object {
        @AppScope
        @Provides
        fun provideWorkManagerInstance(context: Context): androidx.work.WorkManager {
            return androidx.work.WorkManager.getInstance(context)
        }

        @AppScope
        @Provides
        fun provideConnectivityManager(context: Context): ConnectivityManager {
            return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }
    }
}