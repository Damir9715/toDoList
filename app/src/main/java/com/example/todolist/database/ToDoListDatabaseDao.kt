package com.example.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ToDoListDatabaseDao {

    @Insert
    fun insert(task: Task): Long

    @Update
    fun update(task: Task)

    @Query("select * from task where taskId = :key")
    fun get(key: Long): Task

    @Query("select * from task order by taskId desc")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("select * from task where status like :status order by taskId desc")
    fun getAllTasksByStatus(status: String): LiveData<List<Task>>

    @Delete
    fun delete(task: Task)

    @Query("delete from task where taskId in (:list)")
    fun deleteList(list: List<Long>)
}