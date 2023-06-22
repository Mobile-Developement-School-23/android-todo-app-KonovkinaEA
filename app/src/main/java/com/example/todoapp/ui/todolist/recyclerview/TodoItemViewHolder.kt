package com.example.todoapp.ui.todolist.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.TodoitemPreviewBinding
import com.example.todoapp.data.item.TodoItem

class TodoItemViewHolder(binding: TodoitemPreviewBinding) : RecyclerView.ViewHolder(binding.root) {
    private val checkBox = binding.checkBox
    private val todoText = binding.todoText

    fun onBind(todoItem: TodoItem) {
        checkBox.isChecked = todoItem.isDone
        todoText.text = todoItem.text

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            todoItem.isDone = isChecked
        }
    }
}