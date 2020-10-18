package com.example.todolist.ui.overview

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.todolist.R
import com.example.todolist.database.Task
import com.example.todolist.database.TaskStatus
import com.example.todolist.database.ToDoListDatabase
import com.example.todolist.databinding.FragmentOverviewBinding
import com.example.todolist.repository.TaskRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OverviewFragment : Fragment() {
    private val viewModel: OverviewViewModel by viewModels()
    lateinit var binding: FragmentOverviewBinding
    lateinit var taskAdapter: TaskAdapter
    lateinit var fragmentActivity: FragmentActivity

    @Inject
    lateinit var db: ToDoListDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOverviewBinding.inflate(inflater)

        binding.addFab.setOnClickListener(
            Navigation.createNavigateOnClickListener(
                OverviewFragmentDirections.actionOverviewFragmentToEditFragment(Task(taskId = -1))
            )
        )

        fragmentActivity = requireActivity()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        taskAdapter = TaskAdapter(fragmentActivity, viewModel) { task ->
            viewModel.displayEditFragment(task)
        }
        binding.tasksList.adapter = taskAdapter

        viewModel.navigateToEdit.observe(viewLifecycleOwner, {
            it?.let {
                this.findNavController()
                    .navigate(OverviewFragmentDirections.actionOverviewFragmentToEditFragment(it))
                viewModel.displayEditFragmentCompleted()
            }
        })

        setHasOptionsMenu(true)

        val drawerLayout: DrawerLayout = fragmentActivity.findViewById(R.id.drawerLayout)
        val toggle = object : ActionBarDrawerToggle(fragmentActivity, drawerLayout, 0, 0) {

            //todo resets filter when navigation drawer open
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                taskAdapter.closeActionMode()   // close action mode when navDrawer opens
                super.onDrawerSlide(drawerView, slideOffset)
            }
        }
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_to_do -> viewModel.setFilter(TaskStatus.TO_DO)
            R.id.show_in_progress -> viewModel.setFilter(TaskStatus.IN_PROGRESS)
            R.id.show_done -> viewModel.setFilter(TaskStatus.DONE)
            else -> viewModel.setFilter(TaskStatus.ALL)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.filter_menu, menu)
    }

    // hide keyboard on fragment change
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val imm =
            fragmentActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    override fun onPause() {
        super.onPause()
        taskAdapter.closeActionMode()
    }
}