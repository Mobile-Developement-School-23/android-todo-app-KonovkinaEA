package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.api.workmanager.WorkManager
import com.example.todoapp.di.AppComponent
import com.example.todoapp.di.DaggerAppComponent
import javax.inject.Inject

class TodoApp : Application() {
    lateinit var appComponent: AppComponent

    @Inject
    lateinit var workManager: WorkManager

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(applicationContext)
        appComponent.inject(this)
        workManager.setWorkers()
    }
}
