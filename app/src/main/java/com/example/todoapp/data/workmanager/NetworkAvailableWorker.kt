package com.example.todoapp.data.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todoapp.TodoApp
import com.example.todoapp.data.TodoItemsRepository
import javax.inject.Inject

class NetworkAvailableWorker(appContext: Context, workerParams: WorkerParameters)
    : CoroutineWorker(appContext, workerParams) {

    @Inject
    lateinit var repository: TodoItemsRepository

    init {
        (appContext.applicationContext as TodoApp).appComponent.inject(this)
    }

    override suspend fun doWork(): Result {
        repository.loadDataFromServer()
        return Result.success()
    }
}
