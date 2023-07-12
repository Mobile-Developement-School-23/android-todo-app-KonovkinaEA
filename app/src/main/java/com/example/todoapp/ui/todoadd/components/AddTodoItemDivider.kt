package com.example.todoapp.ui.todoadd.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.todoapp.ui.theme.ExtendedTheme
import com.example.todoapp.ui.theme.TodoAppTheme
import com.example.todoapp.ui.todoadd.ThemeModePreview

@Composable
fun AddTodoItemDivider(
    padding: PaddingValues
) {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        color = ExtendedTheme.colors.supportSeparator
    )
}

@Preview
@Composable
fun PreviewAddTodoItemDivider(
    @PreviewParameter(ThemeModePreview::class) darkTheme: Boolean
) {
    TodoAppTheme(darkTheme = darkTheme) {
        AddTodoItemDivider(
            PaddingValues(all = 15.dp)
        )
    }
}
