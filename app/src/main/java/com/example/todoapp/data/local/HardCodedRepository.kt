package com.example.todoapp.data.local

import com.example.todoapp.data.TodoItemsRepository
import com.example.todoapp.data.item.Importance
import com.example.todoapp.data.item.TodoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date

class HardCodedRepository private constructor(): TodoItemsRepository {
    private val todoItems: MutableList<TodoItem> = mutableListOf()
    private var todoItemsFlow: MutableStateFlow<List<TodoItem>>

    init {
        todoItems.addAll(getHardcodedTodoItems())
        todoItemsFlow = MutableStateFlow(todoItems.toList())
    }

    override suspend fun todoItems(): Flow<List<TodoItem>> =
        todoItemsFlow.asStateFlow()

    override suspend fun getTodoItem(id: String) = todoItems.firstOrNull { it.id == id }

    override suspend fun addTodoItem(todoItem: TodoItem) {
        todoItems.add(todoItem)
        updateFlow()
    }

    override suspend fun updateTodoItem(todoItem: TodoItem) {
        val index = todoItems.indexOfFirst { it.id == todoItem.id }
        if (index == -1) return
        todoItems[index] = todoItem
        updateFlow()
    }

    override suspend fun removeTodoItem(id: String) {
        todoItems.removeIf { it.id == id }
        updateFlow()
    }

//    override suspend fun removeTodoItemAt(index: Int) {
//        todoItems.removeAt(index)
//        updateFlow()
//    }

    private fun updateFlow() {
        todoItemsFlow.update {
            todoItems.toList()
        }
    }

    private fun getHardcodedTodoItems(): List<TodoItem> {
        return listOf(
            TodoItem(
                "1", "Закончить проект", Importance.IMPORTANT,
                null, false, Date(), Date()
            ),
            TodoItem(
                "2", "Купить продукты", Importance.BASIC,
                null, false, Date(), Date()
            ),
            TodoItem("3", "Подготовить презентацию", Importance.IMPORTANT,
                Date(), false, Date(), Date()
            ),
            TodoItem(
                "4", "Прочитать книгу", Importance.LOW,
                Date(), false, Date(), Date()),
            TodoItem(
                "5", "Сходить в спортзал", Importance.BASIC,
                null, false, Date(), Date()
            ),
            TodoItem(
                "6", "Записаться на курс программирования", Importance.IMPORTANT,
                Date(), false, Date(), Date()
            ),
            TodoItem(
                "7", "Организовать семейный ужин", Importance.BASIC,
                Date(), false, Date(), Date()
            ),
            TodoItem(
                "8", "Приготовить подарок к дню рождения друга", Importance.LOW,
                null, false, Date(), Date()
            ),
            TodoItem(
                "9",
                "Проверить и отредактировать доклад для конференции по программированию." +
                        "Подготовить презентацию, составить план выступления и подобрать иллюстрации." +
                        "Уделить особое внимание структуре и логической последовательности." +
                        "Проверить правильность использования терминов и грамматических конструкций.",
                Importance.IMPORTANT, Date(), false, Date(), Date()
            ),
            TodoItem(
                "10", "Прогуляться в парке", Importance.LOW,
                null, false, Date(), Date()
            ),
            TodoItem(
                "11", "Завершить исследовательскую работу", Importance.IMPORTANT,
                null, false, Date(), Date()
            ),
            TodoItem(
                "12", "Оплатить счета", Importance.BASIC,
                null, false, Date(), Date()
            ),
            TodoItem(
                "13", "Разработать новый дизайн интерфейса", Importance.IMPORTANT,
                Date(), false, Date(), Date()
            ),
            TodoItem(
                "14", "Посмотреть новый фильм", Importance.LOW,
                Date(), false, Date(), Date()
            ),
            TodoItem(
                "15", "Подготовиться к собеседованию", Importance.BASIC,
                null, false, Date(), Date()
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