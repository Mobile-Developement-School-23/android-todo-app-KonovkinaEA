package com.example.todoapp.data.api.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todoapp.Dependencies

class NetworkUnavailableWorker(appContext: Context, workerParams: WorkerParameters)
    : CoroutineWorker(appContext, workerParams) {
    private val repository = Dependencies.repository

    override suspend fun doWork(): Result {
        repository.loadDataFromDB()
        return Result.success()
    }
}