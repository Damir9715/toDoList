package com.example.todolist.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.database.Task
import com.example.todolist.database.ToDoListDatabaseDao

@Suppress("UNCHECKED_CAST")
class EditViewModelFactory(
    private val task: Task, private val dao: ToDoListDatabaseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditViewModel::class.java)) {
            return EditViewModel(task, dao) as T
        }
        throw IllegalArgumentException("Unknown VM class")
    }
}