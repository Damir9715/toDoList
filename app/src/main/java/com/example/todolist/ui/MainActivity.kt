package com.example.todolist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.todolist.R
import com.example.todolist.databinding.ActivityMainBinding

// todo bug sometimes save fab in editFragment doesn't move with keyboard(stays under)
// todo adapter holds activity and viewModel from fragment
class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        drawerLayout = binding.drawerLayout
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigationHostFragment)
                as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, _ ->
            if (nd.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

//    private fun hideKeyboard() {
//        val view = this.currentFocus
//        if (view != null) {
//            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.hideSoftInputFromWindow(view.windowToken, 0)
//        }
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            android.R.id.home -> hideKeyboard()
//        }
//        return super.onOptionsItemSelected(item)
//    }
}