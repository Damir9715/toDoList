package com.example.todolist.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    var taskId: Long = 0L,
    var title: String,
    var description: String,
    var status: String
) : Parcelable

enum class TaskStatus(val value: String) {
    TO_DO("to_do"), IN_PROGRESS("in_progress"), DONE("done"), ALL("all")
}