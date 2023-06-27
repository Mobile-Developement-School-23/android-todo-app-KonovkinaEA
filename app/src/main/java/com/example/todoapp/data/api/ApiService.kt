package com.example.todoapp.data.api

import com.example.todoapp.data.api.model.AddTodoItemRequest
import com.example.todoapp.data.api.model.ItemContainer
import com.example.todoapp.data.api.model.TodoItemServer
import com.example.todoapp.data.api.model.TodoListServer
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @GET("list")
    suspend fun getAllTodoData(): TodoListServer

    @Headers("Authorization: Bearer prulaurasin")
    @POST("list")
    suspend fun addTodoItem(@Header("X-Last-Known-Revision") revision: String, @Body element: ItemContainer)
}