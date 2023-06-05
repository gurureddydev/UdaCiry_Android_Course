package com.gurureddy.contactmanagerapp.spaceApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.gurureddy.contactmanagerapp.R

class MarsSpaceApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mars_space_app)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_space_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}