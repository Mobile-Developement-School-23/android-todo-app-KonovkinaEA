package com.example.todoapp

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.Repository
import com.example.todoapp.data.TodoItemsRepository
import com.example.todoapp.data.api.workmanager.WorkManager
import com.example.todoapp.di.AppComponent
import com.example.todoapp.di.DaggerAppComponent
import com.example.todoapp.ui.ViewModelFactory
import javax.inject.Inject

class TodoApp : Application() {
    lateinit var appComponent: AppComponent

    @Inject
    lateinit var repository: TodoItemsRepository

    val viewModelFactory: ViewModelProvider.Factory by lazy {
        ViewModelFactory(repository)
    }

    override fun onCreate() {
        super.onCreate()

        Dependencies.init(this)
        appComponent = DaggerAppComponent.factory().create(applicationContext)
        appComponent.inject(this)
        WorkManager.setWorkers(applicationContext)
    }
}
