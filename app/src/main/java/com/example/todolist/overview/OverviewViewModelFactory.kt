package com.example.todolist.overview

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.database.ToDoListDatabaseDao
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class OverviewViewModelFactory(private val dao: ToDoListDatabaseDao,
                               private val application: Application) :
ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
            return OverviewViewModel(dao, application) as T
        }
        throw IllegalArgumentException("Unknown VM class")
    }

}