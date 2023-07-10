package com.example.todoapp.ui.todoadd.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.ui.theme.ExtendedTheme
import com.example.todoapp.ui.todoadd.actions.AddTodoItemUiAction

@Composable
fun AddTodoItemTextField(
    text: String,
    uiAction: (AddTodoItemUiAction) -> Unit
) {
    BasicTextField(
        value = text,
        onValueChange = { uiAction(AddTodoItemUiAction.DescriptionChange(it)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 15.dp),
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = ExtendedTheme.colors.labelPrimary
        ),
        minLines = 3,
        cursorBrush = SolidColor(ExtendedTheme.colors.labelPrimary)
    ) { textField ->
        Card(
            colors = CardDefaults.cardColors(
                containerColor = ExtendedTheme.colors.backSecondary,
                contentColor = ExtendedTheme.colors.labelTertiary
            )
        ) {
            Box(
                modifier = Modifier
                    .padding(15.dp)
            ) {
                if (text.isEmpty())
                    Text(text = stringResource(id = R.string.hint_edit_text))
                textField.invoke()
            }
        }
    }
}

@Preview
@Composable
fun previewAddTodoItemTextField() {
    AddTodoItemTextField(
        text = "a\nff\nff\nfs",
        uiAction = {}
    )
}
