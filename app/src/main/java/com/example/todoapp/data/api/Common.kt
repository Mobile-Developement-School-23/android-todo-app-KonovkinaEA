package com.example.todoapp.data.api

object Common {
    private const val BASE_URL = "https://beta.mrdekk.ru/todobackend/"
    val apiService: ApiService
        get() = ApiClient.getClient(BASE_URL).create(ApiService::class.java)
}