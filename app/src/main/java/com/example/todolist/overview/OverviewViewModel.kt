package com.example.todolist.overview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.database.ToDoListDatabaseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class OverviewViewModel(val dao: ToDoListDatabaseDao) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}