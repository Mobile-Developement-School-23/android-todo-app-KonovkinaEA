package com.example.todoapp.recyclerview.domain

import androidx.recyclerview.widget.DiffUtil
import com.example.todoapp.recyclerview.data.TodoItem

class CustomDiffUtil(
    private val oldList: List<TodoItem>,
    private val newList: List<TodoItem>
): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> false
            oldList[oldItemPosition].text != newList[newItemPosition].text -> false
            oldList[oldItemPosition].importance != newList[newItemPosition].importance -> false
            oldList[oldItemPosition].deadline != newList[newItemPosition].deadline -> false
            oldList[oldItemPosition].isDone != newList[newItemPosition].isDone -> false
            oldList[oldItemPosition].creationDate != newList[newItemPosition].creationDate -> false
            oldList[oldItemPosition].modificationDate != newList[newItemPosition].modificationDate -> false
            else -> true
        }
    }
}