package com.example.todoapp.utils

import android.content.SharedPreferences
import android.content.res.Resources
import android.util.TypedValue
import androidx.appcompat.app.AppCompatDelegate
import com.example.todoapp.data.api.model.TodoItemServer
import com.example.todoapp.data.item.Importance
import com.example.todoapp.data.item.TodoItem
import com.example.todoapp.data.db.entities.Todo
import com.example.todoapp.ui.settings.model.ThemeMode
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun generateRandomItemId(): String = (Date().time / MS_IN_S).toString()
//    SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault()).format(Date())

fun formatLongToDatePattern(date: Long): String =
    SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(unixToDate(date))

fun formatDateToDatePattern(date: Date): String =
    SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(date)

fun stringToImportance(importance: String): Importance {
    return when (importance) {
        "important" -> Importance.IMPORTANT
        "basic" -> Importance.BASIC
        else -> Importance.LOW
    }
}

fun importanceToString(importance: Importance): String {
    return when (importance) {
        Importance.IMPORTANT -> "important"
        Importance.BASIC -> "basic"
        else -> "low"
    }
}

fun dateToUnix(date: Date) = date.time / MS_IN_S

fun unixToDate(date: Long) = Date(date * MS_IN_S)

fun getImportanceId(importance: Importance): Int {
    return when (importance) {
        Importance.IMPORTANT -> IMPORTANCE_IMPORTANT_ID
        Importance.BASIC -> IMPORTANCE_BASIC_ID
        else -> IMPORTANCE_LOW_ID
    }
}

fun getThemeById(themeId: Int): ThemeMode {
    return when (themeId) {
        THEME_LIGHT_ID -> ThemeMode.LIGHT
        THEME_DARK_ID -> ThemeMode.DARK
        else -> ThemeMode.SYSTEM
    }
}

fun getThemeId(theme: ThemeMode): Int {
    return when (theme) {
        ThemeMode.LIGHT -> THEME_LIGHT_ID
        ThemeMode.DARK -> THEME_DARK_ID
        else -> THEME_SYSTEM_ID
    }
}

fun toTodoItemServer(todoItem: TodoItem): TodoItemServer {
    return TodoItemServer(
        id = todoItem.id,
        text = todoItem.text,
        importance = importanceToString(todoItem.importance),
        deadline = todoItem.deadline,
        done = todoItem.isDone,
        createdAt = todoItem.creationDate,
        changedAt = todoItem.modificationDate,
        lastUpdatedBy = "cf1"
    )
}

fun createTodo(todoItem: TodoItem): Todo {
    return Todo(
        id = todoItem.id,
        text = todoItem.text,
        importanceId = getImportanceId(todoItem.importance),
        deadline = todoItem.deadline,
        done = todoItem.isDone,
        createdAt = todoItem.creationDate,
        changedAt = todoItem.modificationDate
    )
}

fun changeThemeMode(themeMode: ThemeMode) {
    when (themeMode) {
        ThemeMode.LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        ThemeMode.DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        ThemeMode.SYSTEM -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }
}

fun saveNotificationsPermission(pref: SharedPreferences, permission: Boolean) {
    pref.edit()
        .putBoolean(NOTIFICATION_PERMISSION_KEY, permission)
        .apply()
}

val Number.toPx
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )
