package com.example.todoapp.ui.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.ui.settings.actions.SettingsUiAction
import com.example.todoapp.ui.theme.Blue
import com.example.todoapp.ui.theme.BlueTranslucent
import com.example.todoapp.ui.theme.ExtendedTheme
import com.example.todoapp.ui.theme.ThemeModePreview
import com.example.todoapp.ui.theme.TodoAppTheme

@Composable
fun SettingsNotifications(
    isNotificationsEnabled: Boolean,
    uiAction: (SettingsUiAction) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.notif_title),
            color = ExtendedTheme.colors.labelPrimary
        )
        Switch(
            checked = isNotificationsEnabled,
            onCheckedChange = { checked -> uiAction(SettingsUiAction.UpdateNotifications(checked)) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Blue,
                checkedTrackColor = BlueTranslucent,
                uncheckedThumbColor = ExtendedTheme.colors.backElevated,
                uncheckedTrackColor = ExtendedTheme.colors.supportOverlay,
                uncheckedBorderColor = ExtendedTheme.colors.supportOverlay,
            )
        )
    }
}

@Preview
@Composable
fun PreviewSettingsNotifications(
    @PreviewParameter(ThemeModePreview::class) darkTheme: Boolean
) {
    TodoAppTheme(darkTheme = darkTheme) {
        SettingsNotifications(
            isNotificationsEnabled = true,
            uiAction = {}
        )
    }
}
