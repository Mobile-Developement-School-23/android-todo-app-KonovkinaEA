package com.example.todoapp.ui.todolist.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.example.todoapp.data.item.TodoItem

class DiffUtilCallback : DiffUtil.ItemCallback<TodoItem>() {
    override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem) =
        oldItem == newItem
}