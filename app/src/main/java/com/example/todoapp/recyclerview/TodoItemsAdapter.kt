package com.example.todoapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.TodoitemPreviewBinding
import com.example.todoapp.recyclerview.data.TodoItem
import com.example.todoapp.recyclerview.domain.CustomDiffUtil

class TodoItemsAdapter(private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<TodoItemViewHolder>() {
    private var oldTodoItemsList = emptyList<TodoItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        return when (viewType) {
            TODO_ITEM_PREVIEW_TYPE -> TodoItemViewHolder(
                TodoitemPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> throw java.lang.IllegalArgumentException("viewType is invalid")
        }
    }

    override fun getItemCount() = oldTodoItemsList.size

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        val todoItem = oldTodoItemsList[position]
        holder.onBind(todoItem)
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(todoItem.id, false)
        }
    }

    fun setData(newTodoItemsList: List<TodoItem>) {
        val diffUtil = CustomDiffUtil(oldTodoItemsList, newTodoItemsList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldTodoItemsList = newTodoItemsList
        diffResult.dispatchUpdatesTo(this)
    }

    interface OnItemClickListener {
        fun onItemClick(id: String, isNewItem: Boolean)
    }

    companion object {
        private const val TODO_ITEM_PREVIEW_TYPE = 0
    }
}