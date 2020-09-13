package com.example.todolist.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.R
import com.example.todolist.database.Task
import com.example.todolist.database.ToDoListDatabase
import com.example.todolist.overview.OverviewViewModel
import com.example.todolist.overview.OverviewViewModelFactory


class EditFragment : Fragment() {

    private lateinit var saveButton: Button
    private lateinit var title: EditText
    private lateinit var description: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_edit, container, false)
        saveButton = view.findViewById(R.id.save_button)
        title = view.findViewById(R.id.title)
        description = view.findViewById(R.id.task_description)

        val application = requireNotNull(this.activity).application
        val dao = ToDoListDatabase.getInstance(application).toDoListDatabaseDao
        val viewModelFactory = OverviewViewModelFactory(dao, application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(OverviewViewModel::class.java)

        saveButton.setOnClickListener {
            val task =
                Task(title = title.text.toString(), description = description.text.toString())
            viewModel.saveTask(task)
        }

        return view
    }
}