package com.example.todoapp.ui.todoadd.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.example.todoapp.R
import com.example.todoapp.ui.theme.Blue
import com.example.todoapp.ui.theme.ExtendedTheme
import com.example.todoapp.ui.theme.TodoAppTheme
import com.example.todoapp.ui.todoadd.ThemeModePreview
import com.example.todoapp.ui.todoadd.actions.AddTodoItemUiAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoItemTopAppBar(
    text: String,
    uiAction: (AddTodoItemUiAction) -> Unit
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(
                onClick = { uiAction(AddTodoItemUiAction.NavigateUp) }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.close_button)
                )
            }
        },
        actions = {
            val saveButtonColor by animateColorAsState(
                targetValue = if (text.isBlank()) ExtendedTheme.colors.labelDisable else Blue,
                label = "save_button_color_animation"
            )
            TextButton(
                onClick = { uiAction(AddTodoItemUiAction.SaveTask) },
                enabled = text.isNotBlank(),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = saveButtonColor,
                    disabledContentColor = saveButtonColor
                )
            ) {
                Text(
                    text = stringResource(R.string.save_button),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ExtendedTheme.colors.backPrimary,
            navigationIconContentColor = ExtendedTheme.colors.labelPrimary
        )
    )
}

@Preview
@Composable
fun PreviewAddTodoItemTopAppBar(
    @PreviewParameter(ThemeModePreview::class) darkTheme: Boolean
) {
    TodoAppTheme(darkTheme = darkTheme) {
        AddTodoItemTopAppBar(
            text = "Text",
            uiAction = {}
        )
    }
}
