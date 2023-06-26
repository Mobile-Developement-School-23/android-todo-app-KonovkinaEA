package com.example.todoapp.retrofit

object Common {
    private val BASE_URL = "https://beta.mrdekk.ru/todobackend"

    val retrofitService: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}