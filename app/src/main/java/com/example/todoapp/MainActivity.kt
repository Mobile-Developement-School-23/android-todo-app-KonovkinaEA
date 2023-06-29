package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.example.todoapp.data.api.network.CheckNetworkConnection

class MainActivity : AppCompatActivity() {
    private lateinit var checkNetworkConnection: CheckNetworkConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        Dependencies.init(applicationContext)
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        callNetworkConnection()

//        val constraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.CONNECTED)
//            .build()
//
//        val request = PeriodicWorkRequest.Builder(DataUpdatesWorker::class.java, 15, TimeUnit.MINUTES)
//            .setConstraints(constraints)
//            .addTag("myWorkManager")
//            .build()
//
//        WorkManager
//            .getInstance(this)
//            .enqueueUniquePeriodicWork(
//                "myWorkManager",
//                ExistingPeriodicWorkPolicy.UPDATE,
//                request
//            )
    }

    private fun callNetworkConnection() {
        checkNetworkConnection = CheckNetworkConnection(application)
        checkNetworkConnection.observe(this) { isConnected ->
            if (isConnected) {
                println("Network available")
            } else {
                println("Network disable")
            }
        }
    }
}