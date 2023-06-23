package com.example.todoapp.ui.todolist.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.TodoitemPreviewBinding
import com.example.todoapp.data.item.TodoItem
import com.example.todoapp.ui.todolist.actions.TodoListUiAction

class TodoItemViewHolder(binding: TodoitemPreviewBinding) : RecyclerView.ViewHolder(binding.root) {
    private lateinit var todoItem: TodoItem
    private val checkBox = binding.checkBox
    private val todoText = binding.todoText

    fun onBind(todoItem: TodoItem, onUiAction: (TodoListUiAction) -> Unit) {
        this.todoItem = todoItem
        checkBox.isChecked = todoItem.isDone
        todoText.text = todoItem.text

        setupDiffCallback(onUiAction)
//        checkBox.setOnCheckedChangeListener { _, isChecked ->
//            todoItem.isDone = isChecked
//        }
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