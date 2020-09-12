package com.example.todolist.database

import androidx.room.*

@Dao
interface ToDoListDatabaseDao {

    @Insert
    fun insert(task: Task)

    @Update
    fun udpate(task: Task)

    @Query("select * from task where taskId = :key")
    fun get(key: Long): Task

    @Delete
    fun deleteAllTasks(tasks: List<Task>): Int

    @Query("select * from task order by taskId desc limit 1")
    fun getLast(): Task?
}