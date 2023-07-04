package com.example.todoapp.utils

import com.example.todoapp.data.api.model.TodoItemServer
import com.example.todoapp.data.item.Importance
import com.example.todoapp.data.item.TodoItem
import com.example.todoapp.data.db.entities.Todo
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

fun toTodoItemServer(todoItem: TodoItem): TodoItemServer {
    return TodoItemServer(
        id = todoItem.id,
        text = todoItem.text,
        importance = importanceToString(todoItem.importance),
        deadline = todoItem.deadline,
        done = todoItem.isDone,
        created_at = todoItem.creationDate,
        changed_at = todoItem.modificationDate,
        last_updated_by = "cf1"
    )
}

fun createTodo(todoItem: TodoItem): Todo {
    return Todo(
        id = todoItem.id,
        text = todoItem.text,
        importanceId = getImportanceId(todoItem.importance),
        deadline = todoItem.deadline,
        done = todoItem.isDone,
        createdAt = todoItem.creationDate,
        changedAt = todoItem.modificationDate
    )
}