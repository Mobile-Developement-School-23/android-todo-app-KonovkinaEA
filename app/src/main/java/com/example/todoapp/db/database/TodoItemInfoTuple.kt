package com.example.todoapp.db.database

import androidx.room.ColumnInfo

data class TodoItemInfoTuple(
    val id: Int,
    var text: String,
    @ColumnInfo(name = "importance_name") var importance: String,
    var deadline: Int,
    var done: Boolean,
    val createdAt: Int,
    var changedAt: Int
)
