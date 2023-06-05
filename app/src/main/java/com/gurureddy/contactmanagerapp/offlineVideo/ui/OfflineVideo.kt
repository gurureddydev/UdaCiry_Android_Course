package com.gurureddy.contactmanagerapp.offlineVideo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.gurureddy.contactmanagerapp.R

class OfflineVideo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offline_video)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_offline_cache)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}