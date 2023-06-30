package com.example.todoapp.data.api.workmanager

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todoapp.Dependencies

class NetworkWorker(appContext: Context, workerParams: WorkerParameters)
    : CoroutineWorker(appContext, workerParams) {
    private val repository = Dependencies.repository

    override suspend fun doWork(): Result {
        if (isNetworkAvailable()) {
            repository.loadDataFromServer()
        } else {
            repository.loadDataFromDB()
        }

        return Result.success()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}