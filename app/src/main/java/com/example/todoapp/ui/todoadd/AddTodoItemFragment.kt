package com.example.todoapp.ui.todoadd

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.TodoApp
import com.example.todoapp.databinding.FragmentAddTodoItemBinding
import com.example.todoapp.di.scope.FragmentScope
import com.example.todoapp.notifications.AlarmReceiver
import com.example.todoapp.ui.theme.TodoAppTheme
import com.example.todoapp.utils.MS_IN_S
import com.example.todoapp.utils.dateToUnix
import kotlinx.coroutines.launch
import javax.inject.Inject

@FragmentScope
class AddTodoItemFragment : Fragment() {
    private var _binding: FragmentAddTodoItemBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<AddTodoItemFragmentArgs>()

    @Inject
    lateinit var viewModel: AddTodoItemViewModel

    @Inject
    lateinit var alarmManager: AlarmManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as TodoApp)
            .appComponent
            .addTodoItemFragmentComponentFactory()
            .create(requireContext())
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
        lifecycleScope.launch {
            val alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
                intent
                    .putExtra("id", args.id.toInt())
                    .putExtra("title", viewModel.uiState.value.text)
                    .putExtra("importance", viewModel.uiState.value.importance.toStringResource())
                PendingIntent.getBroadcast(context, args.id.toInt(), intent, PendingIntent.FLAG_IMMUTABLE)
            }
            if (viewModel.uiState.value.isDeadlineSet) {
            val time = dateToUnix(viewModel.uiState.value.deadline) * MS_IN_S
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    time,
                    alarmIntent
                )
            }
        }

        super.onDestroyView()
        _binding = null
    }
}
