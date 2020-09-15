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
    private lateinit var task: Task
    private lateinit var viewModel: EditViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        task = EditFragmentArgs.fromBundle(requireArguments()).task

        val application = requireNotNull(this.activity).application
        val dao = ToDoListDatabase.getInstance(application).toDoListDatabaseDao
        val viewModelFactory = EditViewModelFactory(task, dao)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EditViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.saveFab.setOnClickListener {
            saveButton(viewModel)
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        saveButton(viewModel)
    }

    private fun saveButton(viewModel: EditViewModel) {
        //if new task
        if (viewModel.selectedTask.value!!.taskId == -1L) {
            viewModel.saveTask(
                Task(
                    title = binding.title.text.toString(),
                    description = binding.taskDescription.text.toString()
                )
            )
        } else {
            viewModel.updateTask(
                Task(
                    taskId = viewModel.selectedTask.value!!.taskId,
                    title = binding.title.text.toString(),
                    description = binding.taskDescription.text.toString()
                )
            )
        }
    }
}