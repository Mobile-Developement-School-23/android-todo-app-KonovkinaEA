package com.example.todoapp.ui.todoadd.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.data.item.Importance
import com.example.todoapp.ui.theme.ExtendedTheme
import com.example.todoapp.ui.theme.Red
import com.example.todoapp.ui.theme.TodoAppTheme
import com.example.todoapp.ui.todoadd.ThemeModePreview
import com.example.todoapp.ui.todoadd.actions.AddTodoItemUiAction

@Composable
fun AddTodoItemImportance(
    importance: Importance,
    uiAction: (AddTodoItemUiAction) -> Unit
) {
    val isImportant = remember(importance) { importance == Importance.IMPORTANT }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .padding(top = 20.dp, bottom = 15.dp)
            .clickable { expanded = true }
    ) {
        Text(
            text = stringResource(id = R.string.title_importance),
            color = ExtendedTheme.colors.labelPrimary
        )
        Text(
            text = stringResource(id = importance.toStringResource()),
            modifier = Modifier.padding(top = 5.dp),
            color = if (isImportant) Red else ExtendedTheme.colors.labelTertiary
        )
        AddTodoItemDropdownMenu(
            uiAction = uiAction,
            closeMenu = { expanded = false },
            expanded
        )
    }
}

@Composable
fun AddTodoItemDropdownMenu(
    uiAction: (AddTodoItemUiAction) -> Unit,
    closeMenu: () -> Unit,
    expanded: Boolean
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = closeMenu,
        modifier = Modifier.background(ExtendedTheme.colors.backElevated),
    ) {
        DropdownMenuItem(
            text = { Text(stringResource(id = R.string.importance_low)) },
            onClick = {
                uiAction(AddTodoItemUiAction.UpdateImportance(Importance.LOW))
                closeMenu()
            },
            colors = MenuDefaults.itemColors(
                textColor = ExtendedTheme.colors.labelPrimary
            )
        )
        DropdownMenuItem(
            text = { Text(stringResource(id = R.string.importance_normal)) },
            onClick = {
                uiAction(AddTodoItemUiAction.UpdateImportance(Importance.BASIC))
                closeMenu()
            },
            colors = MenuDefaults.itemColors(
                textColor = ExtendedTheme.colors.labelPrimary
            )
        )
        DropdownMenuItem(
            text = { Text(stringResource(id = R.string.importance_urgent)) },
            onClick = {
                uiAction(AddTodoItemUiAction.UpdateImportance(Importance.IMPORTANT))
                closeMenu()
            },
            colors = MenuDefaults.itemColors(
                textColor = Red
            )
        )
    }
}

@Preview
@Composable
fun PreviewAddTodoItemImportance(
    @PreviewParameter(ThemeModePreview::class) darkTheme: Boolean
) {
    TodoAppTheme(darkTheme = darkTheme) {
        AddTodoItemImportance(
            importance = Importance.LOW,
            uiAction = {}
        )
    }
}
