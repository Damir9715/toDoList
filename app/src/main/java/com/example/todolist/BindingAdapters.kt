package com.example.todolist

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.database.Task
import com.example.todolist.overview.TaskAdapter

@BindingAdapter("listData")
fun RecyclerView.bindRecyclerView(data: List<Task>?) {
    val adapter = this.adapter as TaskAdapter
    adapter.submitList(data)
}

@BindingAdapter("textBinding")
fun TextView.bindText(text: String?) = text?.let { this.text = text }
