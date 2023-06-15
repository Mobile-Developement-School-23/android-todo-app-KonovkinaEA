package com.example.todoapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.TodoitemPreviewBinding
import com.example.todoapp.recyclerview.data.TodoItem

class TodoItemsAdapter(private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<TodoItemViewHolder>() {
    var todoItemsList = listOf<TodoItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        return when (viewType) {
            TODO_ITEM_PREVIEW_TYPE -> TodoItemViewHolder(
                TodoitemPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> throw java.lang.IllegalArgumentException("viewType is invalid")
        }
    }

    override fun getItemCount() = todoItemsList.size

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        val todoItem = todoItemsList[position]
        holder.onBind(todoItem)
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(todoItem.id, false)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(id: String, isNewItem: Boolean)
    }

    companion object {
        private const val TODO_ITEM_PREVIEW_TYPE = 0
    }
}