package com.example.todoapp.ui.todolist

import androidx.lifecycle.ViewModel
import com.example.todoapp.data.repository.HardCodedRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TodoListViewModel : ViewModel() {
    private val hardCodedRepository = HardCodedRepository.getInstance()

    fun getTodoItems() = hardCodedRepository.getTodoItems()

    fun generateRandomItemId(): String =
        SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault()).format(Date())
}