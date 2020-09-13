package com.example.todolist.overview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.todolist.database.Task
import com.example.todolist.database.ToDoListDatabaseDao
import kotlinx.coroutines.*

class OverviewViewModel(
    val dao: ToDoListDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

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

    fun saveTask(task: Task) {
        uiScope.launch {
            saveTaskToDatabase(task)
        }
    }

    private suspend fun saveTaskToDatabase(task: Task) {
        withContext(Dispatchers.IO) {
            dao.insert(task)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}