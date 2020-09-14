package com.example.todolist.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.R
import com.example.todolist.database.Task
import com.example.todolist.database.ToDoListDatabase
import com.example.todolist.databinding.FragmentEditBinding


class EditFragment : Fragment() {
    private lateinit var binding: FragmentEditBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val dao = ToDoListDatabase.getInstance(application).toDoListDatabaseDao
        val viewModelFactory = EditViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(EditViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val args = arguments?.let { EditFragmentArgs.fromBundle(it) }

        Toast.makeText(context, "TaskId: ${args!!.taskId}", Toast.LENGTH_SHORT).show()

        binding.saveButton.setOnClickListener {
            val task = Task(
                title = binding.title.text.toString(),
                description = binding.taskDescription.text.toString()
            )
            viewModel.saveTask(task)
        }

        return binding.root
    }
}