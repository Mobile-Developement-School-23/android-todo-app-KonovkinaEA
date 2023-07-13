package com.example.todoapp.ui.settings.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.todoapp.ui.settings.actions.SettingsUiEvent
import kotlinx.coroutines.flow.Flow

@Composable
fun SettingsUiActionHandler(
    uiEvent: Flow<SettingsUiEvent>,
    onNavigateUp: () -> Unit,
    showNotificationsToast: () -> Unit
) {
    LaunchedEffect(Unit) {
        uiEvent.collect {
            when (it) {
                SettingsUiEvent.NavigateUp -> onNavigateUp()
                SettingsUiEvent.NotificationsDisable -> showNotificationsToast()
            }
        }
    }
}
