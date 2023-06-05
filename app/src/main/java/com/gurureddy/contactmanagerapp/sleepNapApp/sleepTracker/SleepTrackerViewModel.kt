package com.gurureddy.contactmanagerapp.sleepNapApp.sleepTracker

import android.app.Application
import androidx.lifecycle.*
import com.gurureddy.contactmanagerapp.sleepNapApp.database.SleepDatabaseDao
import com.gurureddy.contactmanagerapp.sleepNapApp.database.SleepNight
import com.gurureddy.contactmanagerapp.sleepNapApp.utill.formatNights
import kotlinx.coroutines.*

class SleepTrackerViewModel(val database: SleepDatabaseDao, application: Application) :
    AndroidViewModel(application) {

    private var toNight = MutableLiveData<SleepNight?>()
    val night = database.getAllNights()
    val nightString = Transformations.map(night) { nights ->
        formatNights(nights, application.resources)
    }

    val startButtonVisible = Transformations.map(toNight) {
        null == it
    }

    val stopButtonVisible = Transformations.map(toNight) {
        null != it
    }

    val clearButtonVisible = Transformations.map(night) {
        it?.isNotEmpty()
    }

    private var _showSnackBarEvent = MutableLiveData<Boolean>()
    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackBarEvent

    private val _navigateToSleepQuality = MutableLiveData<SleepNight?>()
    val navigateToSleepQuality: LiveData<SleepNight?>
        get() = _navigateToSleepQuality


    fun doneNavigating() {
        _navigateToSleepQuality.value = null
    }

    private val _navigateToSleepDataQuality = MutableLiveData<Long?>()
    val navigateToSleepDataQuality
        get() = _navigateToSleepDataQuality

    fun onSleepNightClicked(id: Long) {
        _navigateToSleepDataQuality.value = id
    }

    fun onSleepDataQualityNavigated() {
        _navigateToSleepDataQuality.value = null
    }

    fun doneShowingSnackBar() {
        _showSnackBarEvent.value = false
    }

    init {
        initializeToNight()
    }

    private fun initializeToNight() {
        viewModelScope.launch {
            toNight.value = getTonightFromDatabase()
        }
    }

    private suspend fun getTonightFromDatabase(): SleepNight? {
        return withContext(Dispatchers.IO) {
            var night = database.getTonight()
            if (night?.endTimeMill != night?.startTimeMill) {
                night = null
            }
            night
        }
    }


    fun onStartTracking() {
        viewModelScope.launch {
            val newNight = SleepNight()
            insert(newNight)
            toNight.value = getTonightFromDatabase()
        }
    }

    private suspend fun insert(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.insert(night)
        }
    }

    fun onStopTracking() {
        viewModelScope.launch {
            val oldNight = toNight.value ?: return@launch
            oldNight.endTimeMill = System.currentTimeMillis()
            update(oldNight)
            _navigateToSleepQuality.value = oldNight
        }
    }

    private suspend fun update(night: SleepNight) {
        withContext(Dispatchers.IO) {
            database.update(night)
        }
    }

    fun onClear() {
        viewModelScope.launch {
            clear()
            toNight.value = null
            _showSnackBarEvent.value = true
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }
}
