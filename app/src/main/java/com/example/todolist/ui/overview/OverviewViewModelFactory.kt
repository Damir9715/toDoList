package com.example.todolist.ui.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.repository.TaskRepository

@Suppress("UNCHECKED_CAST")
class OverviewViewModelFactory(
    private val repo: TaskRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
            return OverviewViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown VM class")
    }

}