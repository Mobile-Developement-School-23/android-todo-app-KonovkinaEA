package com.example.todoapp.ui.settings.actions

import com.example.todoapp.ui.settings.ThemeMode

sealed class SettingsUiAction {
    data class UpdateThemeMode(val themeMode: ThemeMode) : SettingsUiAction()

    object NavigateUp: SettingsUiAction()
}
