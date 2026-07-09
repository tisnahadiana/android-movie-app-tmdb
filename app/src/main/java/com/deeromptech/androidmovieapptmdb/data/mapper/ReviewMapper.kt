package com.deeromptech.androidmovieapptmdb.data.mapper

import com.deeromptech.androidmovieapptmdb.core.common.Constants
import com.deeromptech.androidmovieapptmdb.data.remote.dto.ReviewDto
import com.deeromptech.androidmovieapptmdb.domain.model.Review

fun ReviewDto.toDomain(): Review {
    val avatarPath = authorDetails?.avatarPath

    return Review(
        id = id,
        author = author.orEmpty(),
        username = authorDetails?.username.orEmpty(),
        content = content.orEmpty(),
        createdAt = createdAt.orEmpty(),
        rating = authorDetails?.rating,
        avatarUrl = avatarPath?.toAvatarUrl(),
        url = url
    )
}

private fun String.toAvatarUrl(): String {
    return when {
        startsWith("/https://") -> removePrefix("/")
        startsWith("/http://") -> removePrefix("/")
        startsWith("/") -> Constants.TMDB_IMAGE_BASE_URL + this
        else -> Constants.TMDB_IMAGE_BASE_URL + "/$this"
    }
}