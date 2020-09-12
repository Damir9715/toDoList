package com.example.todolist.overview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.todolist.R
import com.example.todolist.database.ToDoListDatabase
import com.example.todolist.databinding.FragmentOverviewBinding


class OverviewFragment : Fragment() {

    lateinit var binding: FragmentOverviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_overview, container, false)

        binding.addFab.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_overviewFragment_to_editFragment)
        )

        val application = requireNotNull(this.activity).application
        val dao = ToDoListDatabase.getInstance(application).toDoListDatabaseDao
        val viewModelFactory = OverviewViewModelFactory(dao, application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(OverviewViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.viewModel!!.getLast()
//        Log.i("asd", "onStart")
    }
}