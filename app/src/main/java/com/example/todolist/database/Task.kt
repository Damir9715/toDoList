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
    var description: String
) : Parcelable