package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.example.todoapp.data.api.workmanager.WorkManagerScheduler

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Dependencies.init(applicationContext)
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)
//        WorkManagerScheduler.refreshPeriodicWork(this)
//        WorkManagerScheduler.patchData(this)
        WorkManagerScheduler.setWorkers(this)
    }
}