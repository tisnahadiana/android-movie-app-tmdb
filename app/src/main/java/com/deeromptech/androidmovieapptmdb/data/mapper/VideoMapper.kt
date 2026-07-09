package com.deeromptech.androidmovieapptmdb.data.mapper

import com.deeromptech.androidmovieapptmdb.core.common.Constants
import com.deeromptech.androidmovieapptmdb.data.remote.dto.VideoDto
import com.deeromptech.androidmovieapptmdb.domain.model.Video

fun VideoDto.toDomain(): Video {
    val videoKey = key.orEmpty()
    val videoSite = site.orEmpty()

    return Video(
        id = id,
        name = name.orEmpty(),
        key = videoKey,
        site = videoSite,
        type = type.orEmpty(),
        official = official ?: false,
        youtubeUrl = if (videoSite.equals("YouTube", ignoreCase = true) && videoKey.isNotBlank()) {
            Constants.YOUTUBE_WATCH_BASE_URL + videoKey
        } else {
            null
        }
    )
}