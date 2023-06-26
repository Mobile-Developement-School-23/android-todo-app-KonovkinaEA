package com.example.todoapp.retrofit

import com.example.todoapp.data.item.TodoItem
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitServices {

    @GET("list")
    fun getTodoList(): Call<MutableList<TodoItem>>
}