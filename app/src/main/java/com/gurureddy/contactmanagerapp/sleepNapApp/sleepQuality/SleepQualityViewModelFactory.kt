package com.gurureddy.contactmanagerapp.sleepNapApp.sleepQuality

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gurureddy.contactmanagerapp.sleepNapApp.database.SleepDatabaseDao

class SleepQualityViewModelFactory(
    private val sleepNightKey: Long,
    val database: SleepDatabaseDao
): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepQualityViewModel::class.java)) {
            return SleepQualityViewModel(sleepNightKey, database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}