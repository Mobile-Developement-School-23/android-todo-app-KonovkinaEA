package com.example.todoapp.data.api.model

import com.google.gson.annotations.SerializedName


data class TodoListServer(
    @SerializedName("list") val todoItems: List<TodoItemServer>? = null,
    @SerializedName("revision") val revision: Long? = null
)
