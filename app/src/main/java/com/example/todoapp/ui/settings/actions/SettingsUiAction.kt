package com.example.todoapp.ui.settings.actions

import com.example.todoapp.ui.settings.model.ThemeMode

sealed class SettingsUiAction {
    data class UpdateThemeMode(val themeMode: ThemeMode) : SettingsUiAction()
    data class UpdateNotifications(val isEnabled: Boolean) : SettingsUiAction()

    object NavigateUp: SettingsUiAction()
}
