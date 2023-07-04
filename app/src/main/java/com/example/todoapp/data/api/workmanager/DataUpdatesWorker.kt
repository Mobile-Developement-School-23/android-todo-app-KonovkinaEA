package com.example.todoapp.data.api.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todoapp.Dependencies

class DataUpdatesWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Dependencies.repository.loadDataFromServer()
        return Result.success()
    }
}