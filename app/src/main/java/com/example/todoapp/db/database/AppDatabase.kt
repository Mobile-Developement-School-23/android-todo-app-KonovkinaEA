package com.example.todoapp.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.db.database.entities.ImportanceLevelsDbEntity
import com.example.todoapp.db.database.entities.RevisionDbEntity
import com.example.todoapp.db.database.entities.TodoDbEntity

@Database(
    version = 2,
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