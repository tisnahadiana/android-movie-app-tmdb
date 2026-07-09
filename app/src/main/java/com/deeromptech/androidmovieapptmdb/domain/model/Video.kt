package com.deeromptech.androidmovieapptmdb.domain.model

data class Video(
    val id: String,
    val name: String,
    val key: String,
    val site: String,
    val type: String,
    val official: Boolean,
    val youtubeUrl: String?
)