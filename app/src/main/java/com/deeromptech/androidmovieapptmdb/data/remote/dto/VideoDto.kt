package com.deeromptech.androidmovieapptmdb.data.remote.dto

data class VideoResponseDto(
    val id: Int,
    val results: List<VideoDto> = emptyList()
)

data class VideoDto(
    val id: String,
    val name: String?,
    val key: String?,
    val site: String?,
    val type: String?,
    val official: Boolean?
)