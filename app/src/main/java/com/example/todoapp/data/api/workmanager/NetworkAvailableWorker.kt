package com.example.todoapp.data.api.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todoapp.Dependencies

class NetworkAvailableWorker(appContext: Context, workerParams: WorkerParameters)
    : CoroutineWorker(appContext, workerParams) {
    private val repository = Dependencies.repository

    override suspend fun doWork(): Result {
        repository.loadDataFromServer()
        return Result.success()
    }
}