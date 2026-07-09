package com.deeromptech.androidmovieapptmdb.data.remote.dto

data class ReviewDto(
    val id: String,
    val author: String?,
    val content: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val url: String?,
    val authorDetails: AuthorDetailsDto?
)

data class AuthorDetailsDto(
    val name: String?,
    val username: String?,
    val avatarPath: String?,
    val rating: Double?
)