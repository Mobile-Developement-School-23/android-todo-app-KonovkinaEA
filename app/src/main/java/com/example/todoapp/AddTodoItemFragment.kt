package com.example.todoapp

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.appcompat.widget.SwitchCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.recyclerview.data.TodoItem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddTodoItemFragment : Fragment() {
    private val args by navArgs<AddTodoItemFragmentArgs>()
    private val calendar = Calendar.getInstance()

//    private val id = args.id
//    private lateinit var todoText: String
//    private lateinit var importance: TodoItem.Importance
//    private lateinit var deadline: String

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

        setTodoText(view)
        setImportance(view)
        setupDeadlineSwitch(view)
    }

    private fun setTodoText(view: View) {
        val textOfTodoItem = view.findViewById<EditText>(R.id.text_of_todo_item)
        textOfTodoItem.setText(args.text)
    }

    private fun setImportance(view: View) {
//        importance = args.importance
        val importanceValue = view.findViewById<TextView>(R.id.importance_value)
        importanceValue.text = args.importance.getLocalizedName(requireContext())
        showPopUpMenu(view)
    }

    private fun showPopUpMenu(view: View) {
        val linearLayout = view.findViewById<LinearLayoutCompat>(R.id.importance)
        linearLayout.setOnClickListener { view ->
            val popupMenu = PopupMenu(requireContext(), view)
            popupMenu.inflate(R.menu.importance_menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_item_high -> {
//                        importance = TodoItem.Importance.URGENT
//                        setTextImportance(view)
                        true
                    }
                    R.id.menu_item_medium -> {
//                        importance = TodoItem.Importance.NORMAL
//                        setTextImportance(view)
                        true
                    }
                    R.id.menu_item_low -> {
//                        importance = TodoItem.Importance.LOW
//                        setTextImportance(view)
                        true
                    }
                    else -> false
                }
            }

            popupMenu.show()
        }
    }

//    private fun setTextImportance(view: View) {
//        val importanceValue = view.findViewById<TextView>(R.id.importance_value)
//        importanceValue.text = importance.getLocalizedName(requireContext())
//    }

    private fun setupDeadlineSwitch(view: View) {
        val switchDeadline = view.findViewById<SwitchCompat>(R.id.switch_deadline)
        val textDeadlineDate = view.findViewById<TextView>(R.id.deadline_date)
        val deadlineDate = args.deadline

        if (deadlineDate != null) {
            textDeadlineDate.text = deadlineDate
            switchDeadline.isChecked = true
        }
        switchDeadline.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                showDatePickerDialog(switchDeadline, textDeadlineDate)
            } else {
                clearDeadlineData(switchDeadline, textDeadlineDate)
            }
        }
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
//        deadline = ""
        textDeadlineDate.text = ""
    }

    private fun showDeadlineData(textDeadlineDate: TextView) {
        val formattedDate = formatDate(calendar.time)
        textDeadlineDate.text = formattedDate
    }

    private fun formatDate(date: Date): String {
        return SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(date)
    }

    private fun backToTodoList() {
        findNavController().navigateUp()
    }

//    private fun getTodoText(view: View) {
//        val textOfTodoItem = view.findViewById<EditText>(R.id.text_of_todo_item)
//        todoText = textOfTodoItem.text.toString()
//    }
}