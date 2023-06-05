package com.gurureddy.contactmanagerapp.trivia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.gurureddy.contactmanagerapp.R
import com.gurureddy.contactmanagerapp.databinding.ActivityTriviaBinding
import timber.log.Timber

class TriviaActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityTriviaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trivia)
        drawerLayout = binding.drawerLayout

        val navController = findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
            if (nd.id == nc.graph.startDestinationId) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }

        NavigationUI.setupWithNavController(binding.navView, navController)

        Timber.i("OnCreate Called")

    }

    override fun onStart() {
        super.onStart()
        Timber.i("OnStart Called")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("OnResume Called")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("OnPause Called")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("OnStop Called")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.i("OnRestart Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("OnDestroy called")
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }
}