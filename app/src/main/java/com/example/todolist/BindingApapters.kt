package com.example.todolist

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.database.Task
import com.example.todolist.overview.TaskAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Task>?) {
    val adapter = recyclerView.adapter as TaskAdapter
    adapter.submitList(data)
}

@BindingAdapter("textBinding")
fun bindText(textView: TextView, text: String?) {
    text?.let {
        textView.text = text
    }
}