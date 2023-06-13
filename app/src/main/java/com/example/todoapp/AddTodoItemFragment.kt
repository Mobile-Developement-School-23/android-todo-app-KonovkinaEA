package com.example.todoapp

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.SwitchCompat
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

        val spinner: Spinner = view.findViewById(R.id.importance_list)
        val importanceList = requireContext().resources.getStringArray(R.array.list_importance).toList()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, importanceList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0)

        val switchDeadline = view.findViewById<SwitchCompat>(R.id.switch_deadline)
        switchDeadline.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                showDatePickerDialog(switchDeadline)
            } else {
                clearDeadlineData(switchDeadline)
            }
        }
    }

    private fun showDatePickerDialog(switchDeadline: SwitchCompat) {
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
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "ОТМЕНА") { _, _ ->
            clearDeadlineData(switchDeadline)
        }
        datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "ГОТОВО") { dialog, _ ->
            showDeadlineData(switchDeadline)
            dialog.dismiss()
        }
    }

    private fun clearDeadlineData(switchDeadline: SwitchCompat) {
        calendar.time = Date()
        switchDeadline.isChecked = false
        switchDeadline.text = ""
    }

    private fun showDeadlineData(switchDeadline: SwitchCompat) {
        val formattedDate = formatDate(calendar.time)
        switchDeadline.text = formattedDate
    }

    private fun formatDate(date: Date): String {
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
    }
}