package com.example.todolist.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.database.Task
import com.example.todolist.databinding.GridItemBinding
import kotlinx.android.synthetic.main.grid_item.view.*

class TaskAdapter(val clickListener: TaskListener) :
    ListAdapter<Task, TaskAdapter.ViewHolder>(TaskDiffCallback()) {

    private var isSelectMode = false
    private val selectedItems = mutableListOf<Task>()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.itemView.setOnClickListener {
            if (isSelectMode) {
                if (item in selectedItems) {
                    holder.itemView.checkBox.isChecked = false
                    selectedItems.remove(item)
                } else {
                    holder.itemView.checkBox.isChecked = true
                    selectedItems.add(item)
                }
            } else
                clickListener.onClick(item)
            println("selectedItem: ${selectedItems.map { it.taskId }}")
        }

        holder.itemView.setOnLongClickListener {
            if (isSelectMode) {
                isSelectMode = false
                holder.binding.checkBox.visibility = View.GONE
                holder.itemView.checkBox.isChecked = false  //todo doesn't uncheck all
                selectedItems.clear()
            } else {
                isSelectMode = true
                holder.binding.checkBox.visibility = View.VISIBLE
                selectedItems.add(item)
            }
            this.notifyDataSetChanged()
            true
        }

        if (isSelectMode) {
            holder.itemView.checkBox.visibility = View.VISIBLE
        } else {
            holder.itemView.checkBox.visibility = View.GONE
        }

        if (item in selectedItems)
            holder.itemView.checkBox.isChecked = true

        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder(val binding: GridItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Task) {
            binding.taskTitle.text = item.title
            binding.taskDescription.text = item.description
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                return ViewHolder(
                    GridItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.taskId == newItem.taskId
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}

class TaskListener(val clickListener: (task: Task) -> Unit) {
    fun onClick(task: Task) = clickListener(task)
}