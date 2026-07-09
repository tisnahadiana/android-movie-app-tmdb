package com.deeromptech.androidmovieapptmdb.data.remote.dto

data class PagedResponseDto<T>(
    val page: Int,
    val results: List<T> = emptyList(),
    val totalPages: Int,
    val totalResults: Int
)