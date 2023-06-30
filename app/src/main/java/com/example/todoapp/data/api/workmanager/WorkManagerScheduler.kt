package com.example.todoapp.data.api.workmanager

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object WorkManagerScheduler {
    private lateinit var workManager: WorkManager

    fun setWorkers(context: Context) {
        workManager = WorkManager.getInstance(context)

        refreshPeriodicWork()
        loadDataFromServer()
    }

    fun reload() {
        loadDataFromServer()
    }

    private fun refreshPeriodicWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequest.Builder(DataUpdatesWorker::class.java, 8, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        workManager
            .enqueueUniquePeriodicWork(
                "refreshWorker",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
    }

    private fun loadDataFromServer() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()

        val request = OneTimeWorkRequest.Builder(NetworkWorker::class.java)
            .setConstraints(constraints)
            .build()

        workManager
            .enqueueUniqueWork(
                "loadWorker",
                ExistingWorkPolicy.KEEP,
                request
            )
    }
}