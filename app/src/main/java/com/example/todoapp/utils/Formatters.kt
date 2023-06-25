package com.example.todoapp.utils

import com.example.todoapp.data.item.Importance
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun generateRandomItemId(): String =
    SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault()).format(Date())

fun formatDate(date: Date): String =
    SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(date)

fun stringToImportance(importance: String): Importance {
    return when (importance) {
        "important" -> Importance.IMPORTANT
        "basic" -> Importance.BASIC
        else -> Importance.LOW
    }
}

fun unixToDate(date: Long?) = if (date != null) Date(date * 1000L) else null

fun dateToUnix(date: Date?) = if (date != null) date.time / 1000 else null

fun getImportanceId(importance: Importance): Int {
    return when (importance) {
        Importance.IMPORTANT -> 3
        Importance.BASIC -> 2
        else -> 1
    }
}
