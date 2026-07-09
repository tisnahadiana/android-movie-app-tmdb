package com.deeromptech.androidmovieapptmdb.domain.model

data class Review(
    val id: String,
    val author: String,
    val username: String,
    val content: String,
    val createdAt: String,
    val rating: Double?,
    val avatarUrl: String?,
    val url: String?
)