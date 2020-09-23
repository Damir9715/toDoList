package com.example.todolist.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.todolist.database.Task
import com.example.todolist.database.TaskStatus
import com.example.todolist.database.ToDoListDatabaseDao

class OverviewViewModel(private val dao: ToDoListDatabaseDao) : ViewModel() {

    private val _navigateToEdit = MutableLiveData<Task>()
    val navigateToEdit
        get() = _navigateToEdit

    var filteredTasks: LiveData<List<Task>>
    private val filterStatus = MutableLiveData("%")

    init {
        filteredTasks = Transformations.switchMap(filterStatus) {
            filterTasks(filterStatus.value!!)
        }
    }

    fun displayEditFragment(task: Task) {
        _navigateToEdit.value = task
    }

    fun displayEditFragmentCompleted() {
        _navigateToEdit.value = null
    }

    fun setFilter(status: TaskStatus) {
        when (status) {
            TaskStatus.ALL -> filterStatus.value = "%"
            else -> filterStatus.value = status.value.toString()
        }
    }

    private fun filterTasks(status: String): LiveData<List<Task>> {
        return dao.getAllTasksByStatus(status)
    }
}