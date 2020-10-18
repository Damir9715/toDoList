package com.example.todolist.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class ToDoListDatabase : RoomDatabase() {
    abstract val dao: ToDoListDatabaseDao
}