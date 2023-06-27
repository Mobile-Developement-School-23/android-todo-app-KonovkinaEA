package com.example.todoapp.utils

import com.example.todoapp.data.api.model.TodoItemServer
import com.example.todoapp.data.item.Importance
import com.example.todoapp.data.item.TodoItem
import com.example.todoapp.data.db.database.TodoItemInfoTuple
import com.example.todoapp.data.db.database.entities.Todo
import com.example.todoapp.data.db.database.entities.TodoDbEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun generateRandomItemId(): String =
    SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault()).format(Date())

fun formatDate(date: Long): String =
    SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(Date(date * 1000L))

fun stringToImportance(importance: String): Importance {
    return when (importance) {
        "important" -> Importance.IMPORTANT
        "basic" -> Importance.BASIC
        else -> Importance.LOW
    }
}

fun importanceToString(importance: Importance): String {
    return when (importance) {
        Importance.IMPORTANT -> "important"
        Importance.BASIC -> "basic"
        else -> "low"
    }
}

fun dateToUnix(date: Date) = date.time / 1000

fun getImportanceId(importance: Importance): Int {
    return when (importance) {
        Importance.IMPORTANT -> 3
        Importance.BASIC -> 2
        else -> 1
    }
}

fun convertToTodo(todoItemServer: TodoItemServer): Todo {
    return Todo(
        id = todoItemServer.id!!.toLong(),
        text = todoItemServer.text!!,
        importanceId = getImportanceId(stringToImportance(todoItemServer.importance!!)),
        deadline = todoItemServer.deadline,
        done = todoItemServer.done!!,
        createdAt = todoItemServer.created_at!!,
        changedAt = todoItemServer.changed_at!!
    )
}

fun createTodo(todoItem: TodoItem): Todo {
    return Todo(
        id = todoItem.id.toLong(),
        text = todoItem.text,
        importanceId = getImportanceId(todoItem.importance),
        deadline = todoItem.deadline,
        done = todoItem.isDone,
        createdAt = todoItem.creationDate,
        changedAt = todoItem.modificationDate
    )
}