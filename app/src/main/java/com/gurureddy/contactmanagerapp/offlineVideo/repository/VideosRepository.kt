package com.gurureddy.contactmanagerapp.offlineVideo.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.gurureddy.contactmanagerapp.offlineVideo.database.VideoDatabase
import com.gurureddy.contactmanagerapp.offlineVideo.database.asDomainModel
import com.gurureddy.contactmanagerapp.offlineVideo.domain.Video
import com.gurureddy.contactmanagerapp.offlineVideo.network.Network
import com.gurureddy.contactmanagerapp.offlineVideo.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
class VideosRepository(private val database: VideoDatabase) {

    val videos: LiveData<List<Video>> = Transformations.map(database.videoDao.getVideos()) {
        it.asDomainModel()
    }

    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            val playlist = Network.devBytes.getPlaylist().await()
            database.videoDao.insertAll(*playlist.asDatabaseModel())
        }
    }
}

