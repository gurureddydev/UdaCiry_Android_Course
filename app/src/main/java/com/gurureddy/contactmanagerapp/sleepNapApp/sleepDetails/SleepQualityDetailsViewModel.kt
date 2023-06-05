package com.gurureddy.contactmanagerapp.sleepNapApp.sleepDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gurureddy.contactmanagerapp.sleepNapApp.database.SleepDatabaseDao
import com.gurureddy.contactmanagerapp.sleepNapApp.database.SleepNight

class SleepQualityDetailsViewModel(
    private val sleepNight: Long = 0L,
    dataSource: SleepDatabaseDao
) : ViewModel() {
    val database = dataSource
    private val night = MediatorLiveData<SleepNight>()

    fun getNight() = night

    init {
        night.addSource(database.getNightWithId(sleepNight), night::setValue)
    }

    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()
    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTracker

    fun doneNavigation() {
        _navigateToSleepTracker.value = null
    }

    fun onClose() {
        _navigateToSleepTracker.value = true
    }
}
