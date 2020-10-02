package com.example.todolist.repository

import androidx.lifecycle.LiveData
import com.example.todolist.database.Task
import com.example.todolist.database.ToDoListDatabaseDao

class TaskRepository(private val dao: ToDoListDatabaseDao) {
    suspend fun saveTaskToDatabase(task: Task): Long {
        return dao.insert(task)
    }

    suspend fun updateTaskFromDatabase(task: Task) {
        dao.update(task)
    }

    suspend fun deleteTaskFromDatabase(task: Task) {
        dao.delete(task)
    }

    suspend fun deleteTasksFromDatabase(task: List<Long>) {
        dao.deleteList(task)
    }

    fun filterTasks(status: String): LiveData<List<Task>> {
        return dao.getAllTasksByStatus(status)
    }
}