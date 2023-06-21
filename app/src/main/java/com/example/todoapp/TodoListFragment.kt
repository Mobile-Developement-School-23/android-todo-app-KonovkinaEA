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
import com.example.todoapp.databinding.FragmentTodoListBinding
import com.example.todoapp.recyclerview.PreviewOffsetTodoItemDecoration
import com.example.todoapp.recyclerview.TodoItemsAdapter
import com.example.todoapp.data.repository.HardCodedRepository
import kotlin.random.Random

class TodoListFragment : Fragment(), TodoItemsAdapter.OnItemClickListener {
    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!
    private val hardCodedRepository = HardCodedRepository.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val todoItemsAdapter = TodoItemsAdapter(this)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.todoItemsList.adapter = todoItemsAdapter
        binding.todoItemsList.layoutManager = layoutManager
        binding.todoItemsList.addItemDecoration(PreviewOffsetTodoItemDecoration(bottomOffset = 16f.toPx.toInt()))
        todoItemsAdapter.setData(hardCodedRepository.getTodoItems())

        binding.floatingActionButton.setOnClickListener {
            onItemClick(Random.nextInt(10000).toString(), true)
        }
    }

    override fun onItemClick(id: String, isNewItem: Boolean) {
        val action = TodoListFragmentDirections.actionTodoListFragmentToAddTodoItemFragment2(id, isNewItem)
        findNavController().navigate(action)
    }
}

val Number.toPx
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )