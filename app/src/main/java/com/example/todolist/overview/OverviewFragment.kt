package com.example.todolist.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.todolist.database.Task
import com.example.todolist.database.ToDoListDatabase
import com.example.todolist.databinding.FragmentOverviewBinding


class OverviewFragment : Fragment() {

    lateinit var binding: FragmentOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOverviewBinding.inflate(inflater)

        binding.addFab.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                OverviewFragmentDirections.actionOverviewFragmentToEditFragment(Task(-1, "", ""))
            )
        )

        val application = requireNotNull(this.activity).application
        val dao = ToDoListDatabase.getInstance(application).toDoListDatabaseDao
        val viewModelFactory = OverviewViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(OverviewViewModel::class.java)

        val manager = GridLayoutManager(application, 2)
        binding.tasksList.layoutManager = manager

        val adapter = TaskAdapter(TaskListener { task ->
            viewModel.onTaskClicked(task)
        })
        binding.tasksList.adapter = adapter

        viewModel.tasks.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToEdit.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController()
                    .navigate(OverviewFragmentDirections.actionOverviewFragmentToEditFragment(it))
                viewModel.onEditNavigated()
            }
        })

        return binding.root
    }
}