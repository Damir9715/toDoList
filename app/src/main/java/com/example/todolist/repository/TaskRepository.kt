package com.example.todolist.repository

import androidx.lifecycle.LiveData
import com.example.todolist.database.Task
import com.example.todolist.database.ToDoListDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository(private val db: ToDoListDatabase) {
    suspend fun saveTaskToDatabase(task: Task): Long {
        return withContext(Dispatchers.IO) {
            db.dao.insert(task)
        }
    }

    suspend fun updateTaskFromDatabase(task: Task) {
        withContext(Dispatchers.IO) {
            db.dao.update(task)
        }
    }

    suspend fun deleteTaskFromDatabase(task: Task) {
        withContext(Dispatchers.IO) {
            db.dao.delete(task)
        }
    }

    fun filterTasks(status: String): LiveData<List<Task>> {
        return db.dao.getAllTasksByStatus(status)
    }
}