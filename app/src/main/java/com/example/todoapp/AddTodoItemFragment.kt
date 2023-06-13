package com.example.todoapp

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.navigation.fragment.findNavController
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddTodoItemFragment : Fragment() {
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_todo_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonClose: ImageButton = view.findViewById(R.id.close_button)
        val buttonSave: Button = view.findViewById(R.id.save_button)
        val buttonDelete: Button = view.findViewById(R.id.delete_button)
        val buttons = listOf(buttonClose, buttonSave, buttonDelete)
        buttons.forEach { button ->
            button.setOnClickListener { backToTodoList() }
        }

        val spinner: Spinner = view.findViewById(R.id.importance_list)
        val importanceList = requireContext().resources.getStringArray(R.array.list_importance).toList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, importanceList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0)

        val switchDeadline = view.findViewById<SwitchCompat>(R.id.switch_deadline)
        val textDeadlineDate = view.findViewById<TextView>(R.id.deadline_date)
        switchDeadline.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                showDatePickerDialog(switchDeadline, textDeadlineDate)
            } else {
                clearDeadlineData(switchDeadline, textDeadlineDate)
            }
        }
    }

    private fun backToTodoList() {
        findNavController().navigateUp()
    }

    private fun showDatePickerDialog(switchDeadline: SwitchCompat, textDeadlineDate: TextView) {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
        datePickerDialog.setOnCancelListener { clearDeadlineData(switchDeadline, textDeadlineDate) }
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "") { dialog, _ ->
            showDeadlineData(textDeadlineDate)
            dialog.dismiss()
        }
    }

    private fun clearDeadlineData(switchDeadline: SwitchCompat, textDeadlineDate: TextView) {
        calendar.time = Date()
        switchDeadline.isChecked = false
        textDeadlineDate.text = ""
    }

    private fun showDeadlineData(textDeadlineDate: TextView) {
        val formattedDate = formatDate(calendar.time)
        textDeadlineDate.text = formattedDate
    }

    private fun formatDate(date: Date): String {
        return SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(date)
    }
}