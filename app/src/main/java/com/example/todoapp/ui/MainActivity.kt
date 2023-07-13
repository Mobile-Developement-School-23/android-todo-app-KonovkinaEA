package com.example.todoapp.ui

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.todoapp.R
import com.example.todoapp.TodoApp
import com.example.todoapp.di.scope.ApplicationScope
import com.example.todoapp.utils.saveNotificationsPermission
import javax.inject.Inject

@ApplicationScope
class MainActivity : AppCompatActivity() {
    private lateinit var pLauncher: ActivityResultLauncher<String>

    @Inject
    lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        (application as TodoApp)
            .appComponent
            .inject(this)
        registerPermissionListener()
        checkNotificationPermission()
    }

    private fun checkNotificationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {}
            shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {}
            else -> {
                pLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun registerPermissionListener() {
        pLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            if (it) {
                saveNotificationsPermission(pref, true)
            } else {
                saveNotificationsPermission(pref, false)
                Toast.makeText(this, R.string.notifications_disable, Toast.LENGTH_LONG).show()
            }
        }
    }
}
