package com.example.todoapp.data.api.model

data class PostContainer(
    val element: TodoItemServer,
    val revision: Long? = null
)
