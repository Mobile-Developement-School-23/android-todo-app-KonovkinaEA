package com.example.todoapp.recyclerview.data

import android.content.Context
import com.example.todoapp.R
import java.util.Date

data class TodoItem(
    val id: String,
    var text: String = "",
    var importance: Importance = Importance.LOW,
    var deadline: Date? = null,
    var isDone: Boolean = false,
    val creationDate: Date = Date(),
    var modificationDate: Date? = null
) {
    enum class Importance {
        LOW {
            override fun getLocalizedName(context: Context): String {
                return context.getString(R.string.importance_low)
            }
        },
        NORMAL {
            override fun getLocalizedName(context: Context): String {
                return context.getString(R.string.importance_normal)
            }
        },
        URGENT {
            override fun getLocalizedName(context: Context): String {
                return context.getString(R.string.importance_urgent)
            }
        };

        abstract fun getLocalizedName(context: Context): String
    }

}