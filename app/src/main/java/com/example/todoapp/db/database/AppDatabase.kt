package com.example.todoapp.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.db.database.entities.ImportanceLevelsDbEntity
import com.example.todoapp.db.database.entities.TodoDbEntity

@Database(
    version = 1,
    entities = [
        ImportanceLevelsDbEntity::class,
        TodoDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getTodoItemDao(): TodoItemDao
}