package com.example.todolist.overview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.database.ToDoListDatabaseDao

class OverviewViewModel(dao: ToDoListDatabaseDao) : ViewModel() {

    private val _navigateToEdit = MutableLiveData<Long>()
    val navigateToEdit
        get() = _navigateToEdit

    fun onTaskClicked(id: Long) {
        _navigateToEdit.value = id
    }

    fun onEditNavigated() {
        _navigateToEdit.value = null
    }

    val tasks = dao.getAllTasks()
}