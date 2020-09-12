package com.example.todolist.overview

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.example.todolist.R
import com.example.todolist.database.Task
import com.example.todolist.database.ToDoListDatabaseDao
import kotlinx.coroutines.*

class OverviewViewModel(
    val dao: ToDoListDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    val _task = MutableLiveData<Task?>()
    val task: LiveData<Task?>
        get() = _task

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

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

    fun getLast() {
        uiScope.launch {
            _task.value = getLastFromDatabase()
        }
    }

    private suspend fun getLastFromDatabase(): Task? {
        return withContext(Dispatchers.IO) {
            dao.getLast()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}