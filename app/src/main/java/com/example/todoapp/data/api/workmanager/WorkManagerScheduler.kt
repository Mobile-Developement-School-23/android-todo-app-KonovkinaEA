package com.example.todoapp.data.api.workmanager

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object WorkManagerScheduler {
    fun setWorkers(context: Context) {
        refreshPeriodicWork(context)
        patchData(context)
    }

    private fun refreshPeriodicWork(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequest.Builder(DataUpdatesWorker::class.java, 8, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        WorkManager
            .getInstance(context)
            .enqueueUniquePeriodicWork(
                "refreshWorker",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
    }

    private fun patchData(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequest.Builder(NetworkPatchWorker::class.java)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context)
            .enqueue(request)
    }
}