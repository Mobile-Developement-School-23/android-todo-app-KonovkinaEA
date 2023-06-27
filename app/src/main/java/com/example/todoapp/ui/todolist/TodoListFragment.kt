package com.example.todoapp.ui.todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.res.Resources
import android.util.TypedValue
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.databinding.FragmentTodoListBinding
import com.example.todoapp.ui.todolist.actions.TodoListUiEvent
import com.example.todoapp.ui.todolist.recyclerview.PreviewOffsetTodoItemDecoration
import com.example.todoapp.ui.todolist.recyclerview.TodoItemsAdapter
import com.example.todoapp.utils.generateRandomItemId
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date

class TodoListFragment : Fragment() {
    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TodoListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUiEventsListener()
        setupRecycler()

        binding.floatingActionButton.setOnClickListener { navigateToNewTodoItem() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUiEventsListener() {
        lifecycleScope.launch {
            viewModel.uiEvent.collectLatest {
                when (it) {
                    is TodoListUiEvent.NavigateToEditTodoItem -> {
                        navigateToEditTodoItem(it.id)
                    }
                    is TodoListUiEvent.NavigateToNewTodoItem -> {
                        navigateToNewTodoItem()
                    }
                }
            }
        }
    }

    private fun setupRecycler() {
        val todoItemsAdapter = TodoItemsAdapter(viewModel::onUiAction)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.todoItemsList.adapter = todoItemsAdapter
        binding.todoItemsList.layoutManager = layoutManager
        binding.todoItemsList.addItemDecoration(PreviewOffsetTodoItemDecoration(bottomOffset = 16f.toPx.toInt()))

        lifecycleScope.launch {
            viewModel.getTodoItems().collectLatest {
                todoItemsAdapter.setData(it)
            }
        }
    }

    private fun navigateToEditTodoItem(id: String) {
        onItemClick(id, false)
    }

    private fun navigateToNewTodoItem() {
        onItemClick(generateRandomItemId(), true)
    }

    private fun onItemClick(id: String, isNewItem: Boolean) {
        val action =
            TodoListFragmentDirections.actionTodoListFragmentToAddTodoItemFragment2(id, isNewItem)
        findNavController().navigate(action)
    }
}

val Number.toPx
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    )