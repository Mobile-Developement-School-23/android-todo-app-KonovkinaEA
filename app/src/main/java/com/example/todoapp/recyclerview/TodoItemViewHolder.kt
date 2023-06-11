package com.example.todoapp.recyclerview

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.recyclerview.data.TodoItem

class TodoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
    private val todoText: TextView = itemView.findViewById(R.id.todo_text)

    fun onBind(todoItem: TodoItem) {
        checkBox.isChecked = todoItem.isDone
        todoText.text = todoItem.text
    }
}