package com.gurureddy.contactmanagerapp.sleepNapApp.sleepTracker

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.gurureddy.contactmanagerapp.R
import com.gurureddy.contactmanagerapp.sleepNapApp.database.SleepNight
import com.gurureddy.contactmanagerapp.sleepNapApp.utill.convertDurationToFormatted
import com.gurureddy.contactmanagerapp.sleepNapApp.utill.convertNumericQualityToString

@BindingAdapter("sleepDuringFormatted")
fun TextView.setSleepDurationFormatted(item: SleepNight?) {
    item?.let {
        text = convertDurationToFormatted(item.startTimeMill, item.endTimeMill, context.resources)
    }
}

@BindingAdapter("sleepQualityString")
fun TextView.setSleepQualityString(item: SleepNight?) {
    item?.let {
        text = convertNumericQualityToString(item.sleepQuality, context.resources)
    }
}

@BindingAdapter("sleepImage")
fun ImageView.setSleepImage(item: SleepNight?) {
    item?.let {
        setImageResource(
            when (item.sleepQuality) {
                0 -> R.drawable.s5
                1 -> R.drawable.s1
                2 -> R.drawable.s2
                3 -> R.drawable.s3
                4 -> R.drawable.s4
                5 -> R.drawable.s6
                else -> R.drawable.sleep
            }
        )
    }
}