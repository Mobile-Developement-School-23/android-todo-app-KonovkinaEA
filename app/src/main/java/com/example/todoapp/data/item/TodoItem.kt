package com.example.todoapp.data.item

import java.util.Date

data class TodoItem(
    val id: String,
    var text: String = "",
    var importance: Importance = Importance.LOW,
    var deadline: Date? = null,
    var isDone: Boolean = false,
    val creationDate: Date = Date(),
    var modificationDate: Date = Date()
)