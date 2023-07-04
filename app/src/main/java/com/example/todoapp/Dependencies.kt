package com.example.todoapp

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.Repository
import com.example.todoapp.data.db.AppDatabase
import com.example.todoapp.ui.ViewModelFactory

object Dependencies {
    private lateinit var appDatabase: AppDatabase

    fun init(context: Context) {
        appDatabase = AppDatabase.getDatabaseInstance(context)
    }

    val repository: Repository by lazy {
        Repository(
            appDatabase.getTodoItemDao(),
            appDatabase.getRevisionDao()
        )
    }

    val viewModelFactory: ViewModelProvider.Factory by lazy {
        ViewModelFactory(repository)
    }
}