package com.gurureddy.contactmanagerapp.offlineVideo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseVideo::class], version = 2, exportSchema = false)
abstract class VideoDatabase : RoomDatabase() {
    abstract val videoDao: VideoDao
}

private lateinit var INSTANCE: VideoDatabase
fun getDatabase(context: Context): VideoDatabase {
    synchronized(VideoDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                VideoDatabase::class.java,
                "videos"
            ).build()
        }
    }
    return INSTANCE
}