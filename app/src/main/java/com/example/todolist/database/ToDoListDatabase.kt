package com.example.todolist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class ToDoListDatabase : RoomDatabase() {

    abstract val dao: ToDoListDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: ToDoListDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context): ToDoListDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ToDoListDatabase::class.java,
                        "to_do_list_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}