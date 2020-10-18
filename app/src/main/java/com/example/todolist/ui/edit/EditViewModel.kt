package com.example.todolist.ui.edit

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.database.Task
import com.example.todolist.repository.TaskRepository
import kotlinx.coroutines.launch

class EditViewModel @ViewModelInject constructor(private val repo: TaskRepository) : ViewModel() {

    private val _selectedTask = MutableLiveData<Task>()
    val selectedTask: LiveData<Task>
        get() = _selectedTask

    fun start(task: Task) {
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