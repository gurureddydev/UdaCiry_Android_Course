package com.gurureddy.contactmanagerapp.offlineVideo.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.gurureddy.contactmanagerapp.offlineVideo.database.getDatabase
import com.gurureddy.contactmanagerapp.offlineVideo.repository.VideosRepository
import kotlinx.coroutines.launch

class DevByteViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val videoRepository = VideosRepository(database)

    init {
        viewModelScope.launch {
            videoRepository.refreshVideos()
        }
    }

    val playList = videoRepository.videos


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DevByteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DevByteViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }
    }
}

