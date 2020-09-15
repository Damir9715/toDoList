package com.example.todolist.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.database.Task
import com.example.todolist.database.ToDoListDatabaseDao
import kotlinx.coroutines.*

class EditViewModel(val task: Task, val dao: ToDoListDatabaseDao) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _selectedTask = MutableLiveData<Task>()
    val selectedTask: LiveData<Task>
        get() = _selectedTask

    init {
        _selectedTask.value = task
    }

    fun saveTask(task: Task) {
        uiScope.launch {
            //set generated id for update not insert on saveButton clicked
            _selectedTask.value!!.taskId = saveTaskToDatabase(task)
        }
    }

    private suspend fun saveTaskToDatabase(task: Task): Long {
        return withContext(Dispatchers.IO) {
            dao.insert(task)
        }
    }

    fun updateTask(task: Task) {
        uiScope.launch {
            updateTaskFromDatabase(task)
        }
    }

    private suspend fun updateTaskFromDatabase(task: Task) {
        withContext(Dispatchers.IO) {
            dao.update(task)
        }
    }
}