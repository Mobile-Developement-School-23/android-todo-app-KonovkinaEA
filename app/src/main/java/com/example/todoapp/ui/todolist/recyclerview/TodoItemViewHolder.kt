package com.example.todoapp.ui.todolist.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.TodoitemPreviewBinding
import com.example.todoapp.data.item.TodoItem
import com.example.todoapp.ui.todolist.actions.TodoListUiAction
import com.example.todoapp.utils.formatLongToDatePattern

class TodoItemViewHolder(binding: TodoitemPreviewBinding) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var todoItem: TodoItem
    private val checkBox = binding.checkBox
    private val todoText = binding.todoText
    private val deadline = binding.deadlineText

    fun onBind(todoItem: TodoItem, onUiAction: (TodoListUiAction) -> Unit) {
        this.todoItem = todoItem
        checkBox.isChecked = todoItem.isDone
        todoText.text = todoItem.text

        val deadlineDate = todoItem.deadline
        if (deadlineDate != null) {
            deadline.text = formatLongToDatePattern(deadlineDate)
        }

        setupDiffCallback(onUiAction)
    }

    private fun setupDiffCallback(onUiAction: (TodoListUiAction) -> Unit) {
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (todoItem.isDone != isChecked) {
                val newItem = todoItem.copy(isDone = isChecked)
                onUiAction(TodoListUiAction.UpdateTodoItem(newItem))
            }
        }
        todoText.setOnClickListener {
            onUiAction(TodoListUiAction.EditTodoItem(todoItem))
        }
    }
}
