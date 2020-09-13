package com.example.todolist.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.database.ToDoListDatabaseDao

@Suppress("UNCHECKED_CAST")
class OverviewViewModelFactory(
    private val dao: ToDoListDatabaseDao
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
            return OverviewViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown VM class")
    }

}