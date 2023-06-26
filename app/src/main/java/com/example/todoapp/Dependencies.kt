package com.example.todoapp

import android.content.Context
import androidx.room.Room
import com.example.todoapp.db.TodoItemsRepository
import com.example.todoapp.db.database.AppDatabase

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

    val todoItemsRepository: TodoItemsRepository by lazy {
        TodoItemsRepository(
            appDatabase.getTodoItemDao(),
            appDatabase.getRevisionDao()
        )
    }
}