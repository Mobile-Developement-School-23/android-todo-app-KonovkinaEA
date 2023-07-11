package com.example.todoapp.ui.todoadd

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.PopupMenu
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.TodoApp
import com.example.todoapp.databinding.FragmentAddTodoItemBinding
import com.example.todoapp.data.item.Importance
import com.example.todoapp.di.scope.FragmentScope
import com.example.todoapp.ui.todoadd.actions.AddTodoItemUiEvent
import com.example.todoapp.utils.dateToUnix
import com.example.todoapp.utils.formatDate
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@FragmentScope
class AddTodoItemFragment : Fragment()/*, DatePickerDialog.OnDateSetListener*/ {
    private var _binding: FragmentAddTodoItemBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<AddTodoItemFragmentArgs>()

    @Inject
    lateinit var viewModel: AddTodoItemViewModel

//    private lateinit var calendar: Calendar

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

        viewModel.findTodoItem(args)
        binding.compose.setContent {

            AddTodoItemScreen(
                uiState = viewModel.uiState.collectAsState().value,
                uiEvent = viewModel.uiEvent,
                onAction = viewModel::onAction,
                onNavigateUp = { findNavController().navigateUp() },
                onSave = { findNavController().navigateUp() }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
