package com.example.todolist.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ToDoListDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task): Long

    @Update
    suspend fun update(task: Task)

    @Query("select * from task where taskId = :key")
    suspend fun get(key: Long): Task

    @Query("select * from task where status like :status order by taskId desc")
    fun getAllTasksByStatus(status: String): LiveData<List<Task>>

    @Delete
    suspend fun delete(task: Task)

    @Query("delete from task where taskId in (:list)")
    suspend fun deleteList(list: List<Long>)
}