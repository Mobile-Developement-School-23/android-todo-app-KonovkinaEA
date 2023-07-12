package com.example.todoapp.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.di.scope.FragmentScope
import com.example.todoapp.ui.settings.actions.SettingsUiAction
import com.example.todoapp.ui.settings.actions.SettingsUiEvent
import com.example.todoapp.ui.settings.model.SettingsState
import com.example.todoapp.ui.settings.model.ThemeMode
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@FragmentScope
class SettingsViewModel(
    themeMode: ThemeMode
) : ViewModel() {
    private val _uiEvent = Channel<SettingsUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _uiState = MutableStateFlow(SettingsState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                uiState.value.copy(themeMode = themeMode)
            }
        }
    }
    fun onUiAction(action: SettingsUiAction) {
        when(action) {
            SettingsUiAction.NavigateUp -> viewModelScope.launch {
                _uiEvent.send(SettingsUiEvent.NavigateUp)
            }
            is SettingsUiAction.UpdateThemeMode -> _uiState.update {
                uiState.value.copy(themeMode = action.themeMode)
            }
        }
    }
}