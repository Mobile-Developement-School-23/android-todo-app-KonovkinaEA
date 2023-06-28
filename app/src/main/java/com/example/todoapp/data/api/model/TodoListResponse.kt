package com.example.todoapp.data.api.model

data class TodoListResponse(
    val list: List<TodoItemServer>?,
    val revision: Long
)
