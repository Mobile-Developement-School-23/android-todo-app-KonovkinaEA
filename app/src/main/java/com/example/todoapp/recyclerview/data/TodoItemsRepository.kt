package com.example.todoapp.recyclerview.data

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
                "9", "Проверить и отредактировать доклад для конференции по программированию. Подготовить презентацию, составить план выступления и подобрать иллюстрации. Уделить особое внимание структуре и логической последовательности. Проверить правильность использования терминов и грамматических конструкций.",
                TodoItem.Importance.URGENT, Date(), false, Date(), null
            ),
            TodoItem(
                "10", "Прогуляться в парке", TodoItem.Importance.LOW,
                null, false, Date(), null
            ),
            TodoItem(
                "11", "Завершить исследовательскую работу", TodoItem.Importance.URGENT,
                null, false, Date(), null
            ),
            TodoItem(
                "12", "Оплатить счета", TodoItem.Importance.NORMAL,
                null, false, Date(), null
            ),
            TodoItem(
                "13", "Разработать новый дизайн интерфейса", TodoItem.Importance.URGENT,
                Date(), false, Date(), null
            ),
            TodoItem(
                "14", "Посмотреть новый фильм", TodoItem.Importance.LOW,
                Date(), false, Date(), Date()
            ),
            TodoItem(
                "15", "Подготовиться к собеседованию", TodoItem.Importance.NORMAL,
                null, false, Date(), null
            )
        )
    }
}