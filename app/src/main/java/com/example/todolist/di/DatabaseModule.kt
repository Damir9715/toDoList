package com.example.todolist.di

import android.content.Context
import androidx.room.Room
import com.example.todolist.database.ToDoListDatabase
import com.example.todolist.database.ToDoListDatabaseDao
import com.example.todolist.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideToDoListDB(@ApplicationContext context: Context): ToDoListDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ToDoListDatabase::class.java,
            "to_do_list_database"
        ).build()
    }

    @Provides
    fun provideDBDao(db: ToDoListDatabase): ToDoListDatabaseDao {
        return db.dao
    }

    @Provides
    fun provideRepository(dao: ToDoListDatabaseDao): TaskRepository {
        return TaskRepository(dao)
    }
}