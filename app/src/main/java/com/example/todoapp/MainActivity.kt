package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.example.todoapp.data.api.ApiClient
import com.example.todoapp.data.api.ApiService
import com.example.todoapp.data.api.AuthInterceptor
import com.example.todoapp.data.api.Common
import com.example.todoapp.data.api.model.ItemContainer
import com.example.todoapp.data.api.model.TodoItemServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Dependencies.init(applicationContext)
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

//        CoroutineScope(Dispatchers.IO).launch {
//            val item = TodoItemServer(
//                id = "567",
//                text = "test",
//                importance = "low",
//                deadline = 4378467,
//                done = false,
//                color = "#FFFFFF",
//                created_at = 7428732,
//                changed_at = 6375334,
//                last_updated_by = "cf21"
//            )
//            Common.apiService.addTodoItem("4", ItemContainer(item))
//        }
    }
}