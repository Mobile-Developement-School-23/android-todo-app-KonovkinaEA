package com.example.todoapp.ui.settings

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.di.scope.FragmentScope
import com.example.todoapp.ui.settings.actions.SettingsUiAction
import com.example.todoapp.ui.settings.actions.SettingsUiEvent
import com.example.todoapp.ui.settings.model.SettingsState
import com.example.todoapp.utils.NOTIFICATION_ON
import com.example.todoapp.utils.NOTIFICATION_PERMISSION_KEY
import com.example.todoapp.utils.THEME_KEY
import com.example.todoapp.utils.THEME_SYSTEM_ID
import com.example.todoapp.utils.changeThemeMode
import com.example.todoapp.utils.getThemeById
import com.example.todoapp.utils.getThemeId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@FragmentScope
class SettingsViewModel(
    private val pref: SharedPreferences
) : ViewModel() {
    private val _uiEvent = Channel<SettingsUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _uiState = MutableStateFlow(SettingsState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            uiState.value.copy(
                themeMode = getThemeById(pref.getInt(THEME_KEY, THEME_SYSTEM_ID)),
                isNotificationsEnabled = pref.getBoolean(
                    NOTIFICATION_ON,
                    pref.getBoolean(NOTIFICATION_PERMISSION_KEY, true)
                )
            )
        }
    }

    fun onUiAction(action: SettingsUiAction) {
        when(action) {
            SettingsUiAction.NavigateUp -> viewModelScope.launch {
                _uiEvent.send(SettingsUiEvent.NavigateUp)
            }
            is SettingsUiAction.UpdateThemeMode -> {
                viewModelScope.launch(Dispatchers.IO) {
                    pref.edit()
                        .putInt(THEME_KEY ,getThemeId(action.themeMode))
                        .apply()
                }
                changeThemeMode(action.themeMode)
                _uiState.update {
                    uiState.value.copy(themeMode = action.themeMode)
                }
            }
            is SettingsUiAction.UpdateNotifications -> {
                if (!pref.getBoolean(NOTIFICATION_PERMISSION_KEY, true)) {
                    viewModelScope.launch {
                        _uiEvent.send(SettingsUiEvent.NotificationsDisable)
                    }
                } else {
                    viewModelScope.launch(Dispatchers.IO) {
                        pref.edit()
                            .putBoolean(NOTIFICATION_ON, action.isEnabled)
                            .apply()
                    }
                    _uiState.update {
                        uiState.value.copy(isNotificationsEnabled = action.isEnabled)
                    }
                }
            }
        }
    }
}
