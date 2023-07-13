package com.example.todoapp.ui.todoadd

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.TodoApp
import com.example.todoapp.databinding.FragmentAddTodoItemBinding
import com.example.todoapp.di.scope.FragmentScope
import com.example.todoapp.ui.theme.TodoAppTheme
import javax.inject.Inject

@FragmentScope
class AddTodoItemFragment : Fragment() {
    private var _binding: FragmentAddTodoItemBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<AddTodoItemFragmentArgs>()

    @Inject
    lateinit var viewModel: AddTodoItemViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as TodoApp)
            .appComponent
            .addTodoItemFragmentComponent()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTodoItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.init(args)
        binding.composeAddTodoItem.setContent {
            TodoAppTheme {
                AddTodoItemScreen(
                    uiState = viewModel.uiState.collectAsState().value,
                    uiEvent = viewModel.uiEvent,
                    uiAction = viewModel::onUiAction,
                    onNavigateUp = { findNavController().navigateUp() },
                    onSave = { findNavController().navigateUp() }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
