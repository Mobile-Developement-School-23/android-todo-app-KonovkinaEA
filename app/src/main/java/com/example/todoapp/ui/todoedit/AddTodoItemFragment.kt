package com.example.todoapp.ui.todoedit

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.PopupMenu
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAddTodoItemBinding
import com.example.todoapp.data.item.Importance
import java.util.Calendar

class AddTodoItemFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private var _binding: FragmentAddTodoItemBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<AddTodoItemFragmentArgs>()
    private val viewModel: AddTodoItemViewModel by viewModels()

    private lateinit var calendar: Calendar

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

        binding.textOfTodoItem.addTextChangedListener { text -> saveButtonState(text) }

        binding.closeButton.setOnClickListener { backToTodoList() }
        binding.saveButton.setOnClickListener { onSaveClick() }
        binding.deleteButton.setOnClickListener { onDeleteClick() }

        binding.textOfTodoItem.setText(viewModel.getText())
        binding.importanceValue.text = viewModel.getImportance().getLocalizedName(requireContext())
        saveButtonState(binding.textOfTodoItem.text)
        showPopUpMenu()
        setupDeadlineSwitch()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveButtonState(text: Editable?) {
        binding.saveButton.isEnabled = !text.isNullOrBlank()
    }

    private fun showPopUpMenu() {
        binding.importance.setOnClickListener { view ->
            val popupMenu = PopupMenu(requireContext(), view)
            popupMenu.inflate(R.menu.importance_menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                val importance = when (menuItem.itemId) {
                    R.id.menu_item_high -> Importance.URGENT
                    R.id.menu_item_medium -> Importance.NORMAL
                    R.id.menu_item_low -> Importance.LOW
                    else -> viewModel.getImportance()
                }
                binding.importanceValue.text = importance.getLocalizedName(requireContext())
                viewModel.setImportance(importance)
                true
            }
            popupMenu.show()
        }
    }

    private fun setupDeadlineSwitch() {
        val deadlineDate = viewModel.getDeadlineDate()

        if (deadlineDate != null) {
            binding.deadlineDate.text = viewModel.formatDate(deadlineDate)
            binding.switchDeadline.isChecked = true
        }
        binding.switchDeadline.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                showDatePickerDialog()
            } else {
                clearDeadlineDate()
            }
        }
    }

    private fun showDatePickerDialog() {
        calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            this,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.setOnCancelListener { binding.switchDeadline.isChecked = false }
        datePickerDialog.show()
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        calendar.set(p1, p2, p3)
        val deadline = calendar.time
        binding.deadlineDate.text = viewModel.formatDate(deadline)
        viewModel.setDeadlineDate(deadline)
    }

    private fun clearDeadlineDate() {
        binding.switchDeadline.isChecked = false
        binding.deadlineDate.text = ""
        viewModel.clearDeadlineDate()
    }

    private fun onSaveClick() {
        viewModel.setText(binding.textOfTodoItem.text.toString())
        viewModel.saveTodoItem()
        backToTodoList()
    }

    private fun onDeleteClick() {
        viewModel.removeTodoItem()
        backToTodoList()
    }

    private fun backToTodoList() {
        findNavController().navigateUp()
    }
}