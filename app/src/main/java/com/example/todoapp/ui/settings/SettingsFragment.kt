package com.example.todoapp.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.navigation.fragment.findNavController
import com.example.todoapp.TodoApp
import com.example.todoapp.databinding.FragmentSettingsBinding
import com.example.todoapp.ui.theme.TodoAppTheme
import javax.inject.Inject

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: SettingsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as TodoApp)
            .appComponent
            .settingsFragmentComponent()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeSettings.setContent {
            TodoAppTheme {
                SettingsScreen(
                    uiState = viewModel.uiState.collectAsState().value,
                    uiEvent = viewModel.uiEvent,
                    uiAction = viewModel::onUiAction,
                    onNavigateUp = { findNavController().navigateUp() }
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
