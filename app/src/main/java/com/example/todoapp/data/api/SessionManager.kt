package com.example.todoapp.data.api

import android.content.Context

class SessionManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)

    init {
        val editor = sharedPreferences.edit()
        editor.putString("auth_token", "prulaurasin")
        editor.apply()
    }

    fun getAuthToken(): String? {
        return sharedPreferences.getString("auth_token", null)
    }

    companion object {
        private var instance: SessionManager? = null

        fun getInstance(context: Context): SessionManager {
            return instance ?: synchronized(this) {
                instance ?: SessionManager(context.applicationContext).also { instance = it }
            }
        }
    }
}
