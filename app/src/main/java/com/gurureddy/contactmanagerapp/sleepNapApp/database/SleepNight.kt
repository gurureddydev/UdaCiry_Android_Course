package com.gurureddy.contactmanagerapp.sleepNapApp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("daily_sleep_quality_table")
data class SleepNight(
    @PrimaryKey(autoGenerate = true)
    var nightId:Long =0L,
    @ColumnInfo("start_time_milli")
    val startTimeMill : Long = System.currentTimeMillis(),
    @ColumnInfo("end_time_milli")
    var endTimeMill : Long = startTimeMill,
    @ColumnInfo("sleep_quality")
    var sleepQuality : Int = -1
)