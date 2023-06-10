package com.example.todoapp.data

import java.util.Date

class TodoItemsRepository {
    private val todoItems: MutableList<TodoItem> = mutableListOf()

    init {
        todoItems.addAll(getHardcodedTodoItems())
    }

    fun getTodoItems(): List<TodoItem> {
        return todoItems.toList()
    }

    fun addTodoItem(todoItem: TodoItem) {
        todoItems.add(todoItem)
    }

    private fun getHardcodedTodoItems(): List<TodoItem> {
        return listOf(
            TodoItem(
                "1", "Закончить проект", TodoItem.Importance.URGENT,
                null, false, Date(), null
            ),
            TodoItem(
                "2", "Купить продукты", TodoItem.Importance.NORMAL,
                null, false, Date(), null
            ),
            TodoItem("3", "Подготовить презентацию", TodoItem.Importance.URGENT,
                Date(), false, Date(), null
            ),
            TodoItem(
                "4", "Прочитать книгу", TodoItem.Importance.LOW,
                Date(), false, Date(), Date()),
            TodoItem(
                "5", "Сходить в спортзал", TodoItem.Importance.NORMAL,
                null, false, Date(), null
            ),
            TodoItem(
                "6", "Записаться на курс программирования", TodoItem.Importance.URGENT,
                Date(), false, Date(), Date()
            ),
            TodoItem(
                "7", "Организовать семейный ужин", TodoItem.Importance.NORMAL,
                Date(), false, Date(), null
            ),
            TodoItem(
                "8", "Приготовить подарок к дню рождения друга", TodoItem.Importance.LOW,
                null, false, Date(), null
            ),
            TodoItem(
                "9", "Посетить конференцию по разработке", TodoItem.Importance.URGENT,
                Date(), false, Date(), null
            ),
            TodoItem(
                "10", "Прогуляться в парке", TodoItem.Importance.LOW,
                null, false, Date(), null
            )
        )
    }
}