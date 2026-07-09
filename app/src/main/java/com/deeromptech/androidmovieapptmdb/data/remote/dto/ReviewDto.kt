package com.deeromptech.androidmovieapptmdb.data.remote.dto

import com.squareup.moshi.Json

data class ReviewDto(
    val id: String,
    val author: String?,
    val content: String?,
    @param:Json(name = "created_at")
    val createdAt: String?,
    @param:Json(name = "updated_at")
    val updatedAt: String?,
    val url: String?,
    @param:Json(name = "author_details")
    val authorDetails: AuthorDetailsDto?
)

data class AuthorDetailsDto(
    val name: String?,
    val username: String?,
    @param:Json(name = "avatar_path")
    val avatarPath: String?,
    val rating: Double?
)