package com.example.todoapp.ui.todoadd.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.ui.theme.Blue
import com.example.todoapp.ui.theme.BlueTranslucent
import com.example.todoapp.ui.theme.ExtendedTheme
import com.example.todoapp.ui.todoadd.actions.AddTodoItemUiAction
import com.example.todoapp.utils.MS_IN_S
import com.example.todoapp.utils.dateToUnix
import com.example.todoapp.utils.formatDate
import java.util.Date

@Composable
fun AddTodoItemDeadline(
    deadline: Date,
    isDateVisible: Boolean,
    uiAction: (AddTodoItemUiAction) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .height(80.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val dateText = remember(deadline) { formatDate(dateToUnix(deadline)) }
        var openDialog by remember { mutableStateOf(false) }

        Column {
            Text(
                text = stringResource(id = R.string.deadline),
                modifier = Modifier.padding(start = 4.dp),
                color = ExtendedTheme.colors.labelPrimary
            )
            AnimatedVisibility(visible = isDateVisible) {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                ) {
                    Text(text = dateText, color = Blue)
                }
            }
        }
        Switch(
            checked = isDateVisible,
            onCheckedChange = {checked ->
                if (checked) {
                    openDialog = true
                } else {
                    AddTodoItemUiAction.UpdateDeadlineVisibility(false)
                }
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Blue,
                checkedTrackColor = BlueTranslucent,
                uncheckedThumbColor = ExtendedTheme.colors.backElevated,
                uncheckedTrackColor = ExtendedTheme.colors.supportOverlay,
                uncheckedBorderColor = ExtendedTheme.colors.supportOverlay,
            )
        )
        DatePicker(
            deadline = deadline,
            isOpen = openDialog,
            uiAction = uiAction,
            closeDialog = { openDialog = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePicker(
    deadline: Date,
    isOpen: Boolean,
    uiAction: (AddTodoItemUiAction) -> Unit,
    closeDialog: () -> Unit
) {
    if (isOpen) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = dateToUnix(deadline) * MS_IN_S
        )
        val confirmEnabled by remember(datePickerState.selectedDateMillis) {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }

        DatePickerDialog(
            onDismissRequest = closeDialog,
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            uiAction(AddTodoItemUiAction.UpdateDeadline(it))
                            uiAction(AddTodoItemUiAction.UpdateDeadlineVisibility(true))
                        }
                        closeDialog()
                    },
                    enabled = confirmEnabled
                ) {
                    Text(stringResource(R.string.ok_button))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = closeDialog
                ) {
                    Text(stringResource(R.string.cancel_button))
                }
            }
        ) {
            androidx.compose.material3.DatePicker(state = datePickerState)
        }
    }
}

@Preview
@Composable
fun PreviewAddTodoItemDeadline() {
    AddTodoItemDeadline(
        deadline = Date(),
        isDateVisible = true,
        uiAction = {}
    )
}
