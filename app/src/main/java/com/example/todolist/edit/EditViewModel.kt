package com.example.todolist.edit

import androidx.lifecycle.ViewModel
import com.example.todolist.database.Task
import com.example.todolist.database.ToDoListDatabaseDao
import kotlinx.coroutines.*

class EditViewModel(val dao: ToDoListDatabaseDao) : ViewModel() {

    private var viewModelJob = Job()
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
}