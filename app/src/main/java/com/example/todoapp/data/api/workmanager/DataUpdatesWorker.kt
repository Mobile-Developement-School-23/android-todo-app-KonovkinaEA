package com.example.todoapp.data.api.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class DataUpdatesWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {

    override fun doWork(): Result {
        println("WORKER: doWork")
        return Result.success()
    }
}