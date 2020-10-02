package com.example.todolist.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todolist.smartTruncate
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    var taskId: Long = 0L,
    var title: String = "",
    var description: String = "",
    var status: Int = 0
) : Parcelable

enum class TaskStatus(val value: Int) {
    TO_DO(0), IN_PROGRESS(1), DONE(2), ALL(3)
}