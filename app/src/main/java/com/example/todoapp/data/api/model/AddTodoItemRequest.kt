package com.example.todoapp.data.api.model

import com.google.gson.annotations.SerializedName

data class AddTodoItemRequest(
    @SerializedName("element") val element: TodoItemServer
)
