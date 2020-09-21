package com.example.todolist.edit

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.database.Task

@Suppress("UNCHECKED_CAST")
class EditViewModelFactory(private val task: Task, val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditViewModel::class.java)) {
            return EditViewModel(task, app) as T
        }
        throw IllegalArgumentException("Unknown VM class")
    }
}