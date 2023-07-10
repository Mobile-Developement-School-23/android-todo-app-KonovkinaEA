package com.example.todoapp

import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.Repository
import com.example.todoapp.data.db.AppDatabase

object Dependencies {
    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }

    private val appDatabase: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db")
            .createFromAsset("todo_database.db")
            .build()
    }

    val repository: Repository by lazy {
        Repository(
            appDatabase.getTodoItemDao(),
            appDatabase.getRevisionDao()
        )
    }
}