package com.example.todoapp.data.db.database

import androidx.room.ColumnInfo
import com.example.todoapp.data.item.TodoItem
import com.example.todoapp.utils.stringToImportance

data class TodoItemInfoTuple(
    val id: Long,
    @ColumnInfo(name = "importance_name") var importance: String,
    var text: String,
    var deadline: Long?,
    var done: Boolean,
    val createdAt: Long,
    var changedAt: Long
) {

    fun toTodoItem(): TodoItem = TodoItem(
        id = id.toString(),
        text = text,
        importance = stringToImportance(importance),
        deadline = deadline,
        isDone = done,
        creationDate = createdAt,
        modificationDate = changedAt
    )
}
