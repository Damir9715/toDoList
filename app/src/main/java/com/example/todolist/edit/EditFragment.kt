package com.example.todolist.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
            if (saveButton(viewModel)) {
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                // back from the stack when save clicked
                findNavController().navigateUp()
            }
        }

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        saveButton(viewModel)
    }

    private fun saveButton(viewModel: EditViewModel): Boolean {
        val id = viewModel.selectedTask.value!!.taskId
        val title = binding.title.text.toString()
        val description = binding.taskDescription.text.toString()
        //doesn't save empty Task
        if (title != "" && description != "") {
            //if new Task
            if (id == -1L) {
                viewModel.saveTask(Task(title = title, description = description))
            } else {
                viewModel.updateTask(Task(taskId = id, title = title, description = description))
            }
            return true
        }
        return false
    }
}