package com.example.todolist.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.database.Task
import com.example.todolist.database.ToDoListDatabase
import com.example.todolist.repository.TaskRepository
import kotlinx.coroutines.launch

class EditViewModel(task: Task, app: Application) : AndroidViewModel(app) {

//    private var viewModelJob = Job()
//    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val db = ToDoListDatabase.getInstance(app)
    private val repo = TaskRepository(db.dao)

    private val _selectedTask = MutableLiveData<Task>()
    val selectedTask: LiveData<Task>
        get() = _selectedTask

    init {
        _selectedTask.value = task
    }

    fun saveTask(task: Task) {
        viewModelScope.launch {
            _selectedTask.value!!.taskId = repo.saveTaskToDatabase(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repo.updateTaskFromDatabase(task)
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repo.deleteTaskFromDatabase(task)
        }
    }
}