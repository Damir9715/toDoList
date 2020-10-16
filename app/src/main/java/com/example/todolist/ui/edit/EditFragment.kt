package com.example.todolist.ui.edit

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.database.Task
import com.example.todolist.database.TaskStatus
import com.example.todolist.databinding.FragmentEditBinding


class EditFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: FragmentEditBinding
    private lateinit var task: Task
    private lateinit var viewModel: EditViewModel
    private var isSaved = false // don't save twice when navigateUp(), it calls onStop
    private lateinit var spinner: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        task = EditFragmentArgs.fromBundle(requireArguments()).task

        val app = requireNotNull(this.activity).application
//        Avoid referencing a View or Activity context in your ViewModel.
//        If the ViewModel outlives the activity (in case of configuration changes),
//        your activity leaks and isn't properly disposed by the garbage collector.
        val viewModelFactory = EditViewModelFactory(task, app)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EditViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        spinner = binding.statusSpinner

        ArrayAdapter.createFromResource(
            requireNotNull(this.activity),
            R.array.status_entries,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
            spinner.setSelection(task.status)
        }

        binding.saveFab.setOnClickListener {
            if (saveButton(viewModel)) {
                isSaved = true
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                // back from the stack when save clicked
                findNavController().navigateUp() //calls onStop
            }
        }

        spinner.onItemSelectedListener = this
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onStop() {
        super.onStop()
        if (!isSaved)
            saveButton(viewModel)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_button -> {
                viewModel.deleteTask(task)
                findNavController().navigateUp()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveButton(viewModel: EditViewModel): Boolean {
        val id = viewModel.selectedTask.value!!.taskId
        val title = binding.title.text.toString()
        val description = binding.taskDescription.text.toString()
        val status = task.status
        //doesn't save empty Task
        if (title != "" && description != "") {
            //if new Task
            if (id == -1L) {
                viewModel.saveTask(Task(title = title, description = description, status = status))
            } else {
                viewModel.updateTask(
                    Task(
                        taskId = id, title = title, description = description, status = status
                    )
                )
            }
            return true
        }
        return false
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        task.status = when (position) {
            0 -> TaskStatus.TO_DO.value
            1 -> TaskStatus.IN_PROGRESS.value
            else -> TaskStatus.DONE.value
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}
}