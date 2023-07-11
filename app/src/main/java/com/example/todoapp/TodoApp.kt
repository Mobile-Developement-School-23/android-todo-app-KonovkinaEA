package com.example.todoapp

import android.app.Application
import com.example.todoapp.data.api.workmanager.CustomWorkManager
import com.example.todoapp.di.AppComponent
import com.example.todoapp.di.DaggerAppComponent
import com.example.todoapp.di.scope.AppScope
import javax.inject.Inject

@AppScope
class TodoApp : Application() {
    lateinit var appComponent: AppComponent

    @Inject
    lateinit var workManager: CustomWorkManager

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(applicationContext)
        appComponent.inject(this)
        workManager.setWorkers()
    }
}
