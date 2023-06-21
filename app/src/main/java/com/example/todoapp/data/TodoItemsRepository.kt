package com.example.todoapp.data

import com.example.todoapp.data.item.TodoItem

interface TodoItemsRepository {
    fun getTodoItems(): List<TodoItem>
    fun getTodoItem(id: String): TodoItem?
    fun addTodoItem(todoItem: TodoItem)
    fun removeTodoItem(id: String)
}