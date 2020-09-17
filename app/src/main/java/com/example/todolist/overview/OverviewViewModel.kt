package com.example.todolist.overview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.database.Task
import com.example.todolist.database.ToDoListDatabaseDao

class OverviewViewModel(dao: ToDoListDatabaseDao) : ViewModel() {

    private val _navigateToEdit = MutableLiveData<Task>()
    val navigateToEdit
        get() = _navigateToEdit

    fun displayEditFragment(task: Task) {
        _navigateToEdit.value = task
    }

    fun displayEditFragmentCompleted() {
        _navigateToEdit.value = null
    }

    val tasks = dao.getAllTasks()
}