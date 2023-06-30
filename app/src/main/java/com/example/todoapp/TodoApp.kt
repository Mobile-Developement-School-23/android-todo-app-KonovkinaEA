package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.api.workmanager.WorkManager

class TodoApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Dependencies.init(applicationContext)
        WorkManager.setWorkers(applicationContext)
    }
}