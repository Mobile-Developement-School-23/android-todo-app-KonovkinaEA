package com.example.todoapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.example.todoapp.data.workmanager.CustomWorkManager
import com.example.todoapp.di.AppComponent
import com.example.todoapp.di.DaggerAppComponent
import com.example.todoapp.di.scope.AppScope
import com.example.todoapp.ui.settings.model.ThemeMode
import com.example.todoapp.utils.changeThemeMode
import javax.inject.Inject

@AppScope
class TodoApp : Application() {
    lateinit var appComponent: AppComponent

    @Inject
    lateinit var workManager: CustomWorkManager

    @Inject
    lateinit var oldThemeMode: ThemeMode

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create(applicationContext)
        appComponent.inject(this)
        workManager.setWorkers()
        changeThemeMode(oldThemeMode)

        val channel = NotificationChannel(
            "channel_id",
            "Channel name",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}
