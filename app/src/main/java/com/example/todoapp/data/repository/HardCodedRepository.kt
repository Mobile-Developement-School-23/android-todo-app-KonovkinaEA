package com.example.todoapp.data.repository

import com.example.todoapp.data.TodoItemsRepository
import com.example.todoapp.data.item.Importance
import com.example.todoapp.data.item.TodoItem
import java.util.Date

class HardCodedRepository private constructor(): TodoItemsRepository {
    private val todoItems: MutableList<TodoItem> = mutableListOf()

    init {
        todoItems.addAll(getHardcodedTodoItems())
    }

    override fun getTodoItems() = todoItems.toList()

    override fun getTodoItem(id: String) = todoItems.firstOrNull { it.id == id }

    override fun addTodoItem(todoItem: TodoItem) {
        todoItems.add(todoItem)
    }

    override fun removeTodoItem(id: String) {
        todoItems.removeIf { it.id == id }
    }

    private fun getHardcodedTodoItems(): List<TodoItem> {
        return listOf(
            TodoItem(
                "1", "Закончить проект", Importance.URGENT,
                null, false, Date(), null
            ),
            TodoItem(
                "2", "Купить продукты", Importance.NORMAL,
                null, false, Date(), null
            ),
            TodoItem("3", "Подготовить презентацию", Importance.URGENT,
                Date(), false, Date(), null
            ),
            TodoItem(
                "4", "Прочитать книгу", Importance.LOW,
                Date(), false, Date(), Date()),
            TodoItem(
                "5", "Сходить в спортзал", Importance.NORMAL,
                null, false, Date(), null
            ),
            TodoItem(
                "6", "Записаться на курс программирования", Importance.URGENT,
                Date(), false, Date(), Date()
            ),
            TodoItem(
                "7", "Организовать семейный ужин", Importance.NORMAL,
                Date(), false, Date(), null
            ),
            TodoItem(
                "8", "Приготовить подарок к дню рождения друга", Importance.LOW,
                null, false, Date(), null
            ),
            TodoItem(
                "9",
                "Проверить и отредактировать доклад для конференции по программированию." +
                        "Подготовить презентацию, составить план выступления и подобрать иллюстрации." +
                        "Уделить особое внимание структуре и логической последовательности." +
                        "Проверить правильность использования терминов и грамматических конструкций.",
                Importance.URGENT, Date(), false, Date(), null
            ),
            TodoItem(
                "10", "Прогуляться в парке", Importance.LOW,
                null, false, Date(), null
            ),
            TodoItem(
                "11", "Завершить исследовательскую работу", Importance.URGENT,
                null, false, Date(), null
            ),
            TodoItem(
                "12", "Оплатить счета", Importance.NORMAL,
                null, false, Date(), null
            ),
            TodoItem(
                "13", "Разработать новый дизайн интерфейса", Importance.URGENT,
                Date(), false, Date(), null
            ),
            TodoItem(
                "14", "Посмотреть новый фильм", Importance.LOW,
                Date(), false, Date(), Date()
            ),
            TodoItem(
                "15", "Подготовиться к собеседованию", Importance.NORMAL,
                null, false, Date(), null
            )
        )
    }

    companion object {
        @Volatile
        private var instance: HardCodedRepository? = null

        fun getInstance(): HardCodedRepository {
            return instance ?: synchronized(this) {
                instance ?: HardCodedRepository().also { instance = it }
            }
        }
    }
}