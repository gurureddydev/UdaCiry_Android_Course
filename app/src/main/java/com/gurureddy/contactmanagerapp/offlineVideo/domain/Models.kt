package com.gurureddy.contactmanagerapp.offlineVideo.domain

import com.gurureddy.contactmanagerapp.offlineVideo.utill.smartTruncate

data class Video(
    val title: String,
    val description: String,
    val url: String,
    val updated: String,
    val thumbnail: String
) {
    val shortDescription: String
        get() = description.smartTruncate(100)
}




