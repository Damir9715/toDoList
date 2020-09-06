package com.example.todolist.overview

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.todolist.database.ToDoListDatabaseDao

class OverviewViewModel(
    val dao: ToDoListDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    val ss = "sfsdfsd"

    fun addButton() {
        Log.i("fuck", "hey you")
    }
}