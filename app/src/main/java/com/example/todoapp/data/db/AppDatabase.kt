package com.example.todoapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.data.db.entities.ImportanceLevelsDbEntity
import com.example.todoapp.data.db.entities.RevisionDbEntity
import com.example.todoapp.data.db.entities.TodoDbEntity

@Database(
    version = 4,
    entities = [
        ImportanceLevelsDbEntity::class,
        TodoDbEntity::class,
        RevisionDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getTodoItemDao(): TodoItemDao

    abstract fun getRevisionDao(): RevisionDao
}