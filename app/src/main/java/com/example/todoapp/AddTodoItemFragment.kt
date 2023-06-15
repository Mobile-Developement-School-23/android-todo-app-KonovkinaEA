package com.example.todoapp

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
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.databinding.FragmentAddTodoItemBinding
import com.example.todoapp.recyclerview.data.TodoItem
import com.example.todoapp.recyclerview.data.TodoItemsRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddTodoItemFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private var _binding: FragmentAddTodoItemBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<AddTodoItemFragmentArgs>()
    private val todoItemsRepository = TodoItemsRepository.getInstance()
    private lateinit var calendar: Calendar

    private lateinit var todoItem: TodoItem
    private var importance = TodoItem.Importance.LOW
    private var deadline: Date? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTodoItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        todoItem = todoItemsRepository.getTodoItem(args.id) ?: TodoItem(
            id = args.id
        )

        binding.textOfTodoItem.addTextChangedListener { text -> buttonsState(text) }

        binding.closeButton.setOnClickListener { backToTodoList() }
        binding.saveButton.setOnClickListener { onSaveClick() }
        binding.deleteButton.setOnClickListener { onDeleteClick() }

        binding.textOfTodoItem.setText(todoItem.text)
        binding.importanceValue.text = todoItem.importance.getLocalizedName(requireContext())
        buttonsState(binding.textOfTodoItem.text)
        showPopUpMenu()
        setupDeadlineSwitch()
    }

    private fun buttonsState(text: Editable?) {
        val hasText = !text.isNullOrBlank()
        binding.saveButton.isEnabled = hasText
        binding.deleteButton.isEnabled = hasText
    }

    private fun showPopUpMenu() {
        binding.importance.setOnClickListener { view ->
            val popupMenu = PopupMenu(requireContext(), view)
            popupMenu.inflate(R.menu.importance_menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_item_high -> {
                        importance = TodoItem.Importance.URGENT
                        setTextImportance()
                        true
                    }
                    R.id.menu_item_medium -> {
                        importance = TodoItem.Importance.NORMAL
                        setTextImportance()
                        true
                    }
                    R.id.menu_item_low -> {
                        importance = TodoItem.Importance.LOW
                        setTextImportance()
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    private fun setTextImportance() {
        binding.importanceValue.text = importance.getLocalizedName(requireContext())
    }

    private fun setupDeadlineSwitch() {
        val deadlineDate = todoItem.deadline

        if (deadlineDate != null) {
            binding.deadlineDate.text = formatDate(deadlineDate)
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
        datePickerDialog.show()
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        calendar.set(p1, p2, p3)
        deadline = calendar.time
        binding.deadlineDate.text = formatDate(calendar.time)
    }

    private fun clearDeadlineDate() {
        binding.switchDeadline.isChecked = false
        deadline = null
        binding.deadlineDate.text = ""
    }

    private fun formatDate(date: Date): String {
        return SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(date)
    }



    private fun backToTodoList() {
        findNavController().navigateUp()
    }

    private fun onSaveClick() {
        todoItem.text = binding.textOfTodoItem.text.toString()
        todoItem.importance = importance
        todoItem.deadline = deadline
        if (args.isNewItem) {
            todoItemsRepository.addTodoItem(todoItem)
        } else {
            todoItem.modificationDate = Calendar.getInstance().time
        }
        backToTodoList()
    }

    private fun onDeleteClick() {
        if (!args.isNewItem) todoItemsRepository.removeTodoItem(args.id)
        backToTodoList()
    }
}