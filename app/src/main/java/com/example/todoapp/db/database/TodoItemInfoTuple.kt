package com.example.todoapp.db.database

import androidx.room.ColumnInfo

data class TodoItemInfoTuple(
    val id: Long,
    @ColumnInfo(name = "importance_name") var importance: String,
    var text: String,
    var deadline: Long?,
    var done: Boolean,
    val createdAt: Long,
    var changedAt: Long
)
