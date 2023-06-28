package com.example.todoapp.data.api

import com.example.todoapp.data.api.model.ItemContainer
import com.example.todoapp.data.api.model.PostContainer
import com.example.todoapp.data.api.model.TodoListServer
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ApiService {

    @GET("list")
    suspend fun getAllTodoData(): Response<TodoListServer>

    @POST("list")
    suspend fun addTodoItem(@Header("X-Last-Known-Revision") revision: String, @Body element: ItemContainer): Response<PostContainer>

    @PATCH("list")
    suspend fun patchList(@Header("X-Last-Known-Revision") revision: String, @Body list: TodoListServer): Response<TodoListServer>
}