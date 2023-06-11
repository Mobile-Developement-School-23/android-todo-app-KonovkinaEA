package com.example.todoapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todoapp.R
import com.example.todoapp.recyclerview.data.TodoItem

class TodoItemsAdapter : RecyclerView.Adapter<ViewHolder>() {
    var todoItemsList = listOf<TodoItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TODO_ITEM_PREVIEW_TYPE -> TodoItemViewHolder(
                layoutInflater.inflate(
                    R.layout.todoitem_preview,
                    parent,
                    false
                )
            )
            else -> throw java.lang.IllegalArgumentException("viewType is invalid")
        }
    }

    override fun getItemCount() = todoItemsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is TodoItemViewHolder -> holder.onBind(todoItemsList[position] as TodoItem)
        }
    }

    companion object {
        private const val TODO_ITEM_PREVIEW_TYPE = 0
    }
}