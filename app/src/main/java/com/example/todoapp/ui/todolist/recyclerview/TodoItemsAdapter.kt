package com.example.todoapp.ui.todolist.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.TodoitemPreviewBinding
import com.example.todoapp.data.item.TodoItem
import com.example.todoapp.ui.todolist.actions.TodoListUiAction

class TodoItemsAdapter(private val onUiAction: (TodoListUiAction) -> Unit
) : RecyclerView.Adapter<TodoItemViewHolder>() {
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
        holder.onBind(todoItem, onUiAction)
    }

    fun setData(newTodoItemsList: List<TodoItem>) {
        val diffUtil = CustomDiffUtil(oldTodoItemsList, newTodoItemsList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldTodoItemsList = newTodoItemsList
        diffResult.dispatchUpdatesTo(this)
    }

    companion object {
        private const val TODO_ITEM_PREVIEW_TYPE = 0
    }
}
