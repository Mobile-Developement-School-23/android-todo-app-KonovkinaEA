package com.example.todoapp.data.api.model

data class TodoListResponse(
    val todoItems: List<TodoItemServer>?,
    val revision: Long
)
