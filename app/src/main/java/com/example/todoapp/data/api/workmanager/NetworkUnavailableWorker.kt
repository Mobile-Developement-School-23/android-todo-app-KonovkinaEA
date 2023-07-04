package com.example.todoapp.data.api.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todoapp.data.Repository
import com.example.todoapp.data.TodoItemsRepository
import javax.inject.Inject

class NetworkUnavailableWorker(appContext: Context, workerParams: WorkerParameters)
    : CoroutineWorker(appContext, workerParams) {

    @Inject
    lateinit var repository: TodoItemsRepository

    override suspend fun doWork(): Result {
        repository.loadDataFromDB()
        return Result.success()
    }
}