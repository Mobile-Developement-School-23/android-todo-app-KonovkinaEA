package com.example.todoapp.data.item

import android.content.Context
import com.example.todoapp.R

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