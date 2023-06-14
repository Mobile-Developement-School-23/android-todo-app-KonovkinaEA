package com.example.todoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.res.Resources
import android.util.TypedValue
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.recyclerview.PreviewOffsetTodoItemDecoration
import com.example.todoapp.recyclerview.TodoItemsAdapter
import com.example.todoapp.recyclerview.data.TodoItem
import com.example.todoapp.recyclerview.data.TodoItemsRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.random.Random

class TodoListFragment : Fragment(), TodoItemsAdapter.OnItemClickListener {
//    private val args by navArgs<TodoListFragmentArgs>()
    private lateinit var todoItemsRecyclerView: RecyclerView
    private val todoItemsRepository = TodoItemsRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todoItemsRecyclerView = view.findViewById(R.id.todo_items_list)
        val todoItemsAdapter = TodoItemsAdapter(this)
        val layoutManager = LinearLayoutManager(requireContext())
        todoItemsRecyclerView.adapter = todoItemsAdapter
        todoItemsRecyclerView.layoutManager = layoutManager
        todoItemsRecyclerView.addItemDecoration(PreviewOffsetTodoItemDecoration(bottomOffset = 16f.toPx.toInt()))
        todoItemsAdapter.todoItemsList = todoItemsRepository.getTodoItems()

        val floatingActionButton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener { onItemClick(emptyTodoItem()) }
    }

    override fun onItemClick(todoItem: TodoItem) {
        val deadline = if (todoItem.deadline != null) {
            SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(todoItem.deadline)
        } else {
            null
        }
        val action = TodoListFragmentDirections.actionTodoListFragmentToAddTodoItemFragment2(
            todoItem.id,
            todoItem.text,
            todoItem.importance,
            deadline
        )
        findNavController().navigate(action)
    }

//    private fun addNewItemClick() {
//        val action = TodoListFragmentDirections.actionTodoListFragmentToAddTodoItemFragment2(
//            id = Random.nextInt(10000).toString()
//        )
//        findNavController().navigate(action)
//    }

    private fun emptyTodoItem(): TodoItem {
        return TodoItem(id = Random.nextInt(10000).toString())
    }
}

val Number.toPx
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )