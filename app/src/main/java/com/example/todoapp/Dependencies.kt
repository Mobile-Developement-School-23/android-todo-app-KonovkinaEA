package com.example.todoapp

import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.db.TodoItemsDbRepository
import com.example.todoapp.data.db.database.AppDatabase

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

    val todoItemsDbRepository: TodoItemsDbRepository by lazy {
        TodoItemsDbRepository(
            appDatabase.getTodoItemDao()
        )
    }
}