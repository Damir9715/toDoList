package com.example.todolist.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    var taskId: Long = 0L,
    var title: String,
    var description: String
)