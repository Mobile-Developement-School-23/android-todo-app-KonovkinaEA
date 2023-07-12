package com.example.todoapp.ui.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.example.todoapp.ui.settings.actions.SettingsUiAction
import com.example.todoapp.ui.settings.actions.SettingsUiEvent
import com.example.todoapp.ui.settings.components.SettingsTheme
import com.example.todoapp.ui.settings.components.SettingsTopAppBar
import com.example.todoapp.ui.settings.components.SettingsUiActionHandler
import com.example.todoapp.ui.settings.model.ThemeMode
import com.example.todoapp.ui.theme.ExtendedTheme
import com.example.todoapp.ui.theme.ThemeModePreview
import com.example.todoapp.ui.theme.TodoAppTheme
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun SettingsScreen(
    themeMode: ThemeMode,
    uiEvent: Flow<SettingsUiEvent>,
    uiAction: (SettingsUiAction) -> Unit,
    onNavigateUp: () -> Unit
) {
    SettingsUiActionHandler(
        uiEvent = uiEvent,
        onNavigateUp = onNavigateUp
    )

    Scaffold(
        topBar = { SettingsTopAppBar(uiAction = uiAction) },
        containerColor = ExtendedTheme.colors.backPrimary
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                SettingsTheme(
                    themeMode = themeMode,
                    uiAction = uiAction
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSettingsScreen(
    @PreviewParameter(ThemeModePreview::class) darkTheme: Boolean
) {
    val uiEvent = Channel<SettingsUiEvent>().receiveAsFlow()

    TodoAppTheme(darkTheme = darkTheme) {
        SettingsScreen(
            themeMode = if (darkTheme) ThemeMode.DARK else ThemeMode.LIGHT,
            uiEvent = uiEvent,
            uiAction = {},
            onNavigateUp = {}
        )
    }
}
