package com.example.todolist.ui.overview

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.todolist.database.Task
import com.example.todolist.database.TaskStatus
import com.example.todolist.repository.TaskRepository
import kotlinx.coroutines.launch

class OverviewViewModel @ViewModelInject constructor(private val repo: TaskRepository) :
    ViewModel() {

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
        return repo.filterTasks(status)
    }

    fun deleteList(list: List<Long>) {
        viewModelScope.launch {
            repo.deleteTasksFromDatabase(list)
        }
    }
}