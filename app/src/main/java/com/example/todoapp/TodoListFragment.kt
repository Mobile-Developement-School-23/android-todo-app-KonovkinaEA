package com.example.todoapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.res.Resources
import android.util.TypedValue
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.recyclerview.PreviewOffsetTodoItemDecoration
import com.example.todoapp.recyclerview.TodoItemsAdapter
import com.example.todoapp.recyclerview.data.TodoItemsRepository
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoListFragment : Fragment(), TodoItemsAdapter.OnItemClickListener {
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
        floatingActionButton.setOnClickListener { onItemClick() }
    }

    override fun onItemClick() {
        val addTodoItemFragment = AddTodoItemFragment()
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.todo_list_fragment, addTodoItemFragment)
            .addToBackStack(null)
            .commit()
    }
}

val Number.toPx
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )