package com.example.todoapp.di.module

import android.content.Context
import android.net.ConnectivityManager
import androidx.work.WorkManager
import com.example.todoapp.data.api.workmanager.CustomWorkManager
import com.example.todoapp.di.scope.AppScope
import dagger.Module
import dagger.Provides

@Module
interface WorkerModule {
    companion object {
        @AppScope
        @Provides
        fun provideWorkManager(
            workManager: WorkManager,
            connectivityManager: ConnectivityManager
        ): CustomWorkManager {
            return CustomWorkManager(workManager, connectivityManager)
        }
        @AppScope
        @Provides
        fun provideWorkManagerInstance(context: Context): WorkManager {
            return WorkManager.getInstance(context)
        }

        @AppScope
        @Provides
        fun provideConnectivityManager(context: Context): ConnectivityManager {
            return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }
    }
}