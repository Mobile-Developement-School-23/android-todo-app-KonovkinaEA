package com.example.todoapp.data

import java.util.Date

data class TodoItem(
    val id: String,
    val text: String,
    val importance: Importance,
    val deadline: Date?,
    val isDone: Boolean,
    val creationDate: Date,
    val modificationDate: Date?
) {
    enum class Importance {
        LOW, NORMAL, URGENT
    }
}