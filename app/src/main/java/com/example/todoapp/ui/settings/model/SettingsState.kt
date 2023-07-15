package com.example.todoapp.ui.settings.model

data class SettingsState(
    val themeMode: ThemeMode = ThemeMode.LIGHT,
    val isNotificationsEnabled: Boolean = true
)
