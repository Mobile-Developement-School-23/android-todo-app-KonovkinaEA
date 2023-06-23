package com.example.todoapp.ui.todoedit

import androidx.lifecycle.ViewModel
import com.example.todoapp.data.item.Importance
import com.example.todoapp.data.item.TodoItem
import com.example.todoapp.data.repository.HardCodedRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddTodoItemViewModel : ViewModel() {
    private val hardCodedRepository = HardCodedRepository.getInstance()
    private lateinit var todoItem: TodoItem
    private var text: String = ""
    private var importance = Importance.LOW
    private var deadline: Date? = null
    private var isNewItem: Boolean = true

    fun findTodoItem(args: AddTodoItemFragmentArgs) {
        if (args.isNewItem) todoItem = TodoItem(id = args.id)
        else {
            todoItem = hardCodedRepository.getTodoItem(args.id)!!
            text = todoItem.text
            importance = todoItem.importance
            deadline = todoItem.deadline
            isNewItem = false
        }
    }

    fun getText() = text

    fun setText(text: String) {
        this.text = text
    }

    fun getImportance() = importance

    fun setImportance(importance: Importance) {
        this.importance = importance
    }

    fun setDeadlineDate(deadline: Date) {
        this.deadline = deadline
    }

    fun getDeadlineDate() = deadline

    fun clearDeadlineDate() {
        deadline = null
    }

    fun saveTodoItem() {
        todoItem.text = text
        todoItem.importance = importance
        todoItem.deadline = deadline
        if (isNewItem) {
            hardCodedRepository.addTodoItem(todoItem)
        } else {
            todoItem.modificationDate = Date()
        }
    }

    fun removeTodoItem() {
        if (!isNewItem) hardCodedRepository.removeTodoItem(todoItem.id)
    }

    fun formatDate(date: Date): String {
        return SimpleDateFormat("d MMMM yyyy", Locale.getDefault()).format(date)
    }
}