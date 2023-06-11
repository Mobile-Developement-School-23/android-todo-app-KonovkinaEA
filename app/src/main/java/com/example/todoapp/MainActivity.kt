package com.example.todoapp

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.recyclerview.PreviewOffsetTodoItemDecoration
import com.example.todoapp.recyclerview.TodoItemsAdapter
import com.example.todoapp.recyclerview.data.TodoItemsRepository

class MainActivity : AppCompatActivity() {
    private lateinit var todoItemsRecyclerView: RecyclerView
    private val todoItemsRepository = TodoItemsRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoItemsRecyclerView = findViewById(R.id.todo_items)
        val todoItemsAdapter = TodoItemsAdapter()
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        todoItemsRecyclerView.adapter = todoItemsAdapter
        todoItemsRecyclerView.layoutManager = layoutManager
        todoItemsRecyclerView.addItemDecoration(PreviewOffsetTodoItemDecoration(bottomOffset = 16f.toPx.toInt()))
        todoItemsAdapter.todoItemsList = todoItemsRepository.getTodoItems()
    }
}

val Number.toPx
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )