package com.deeromptech.androidmovieapptmdb.data.remote.dto

import com.squareup.moshi.Json

data class PagedResponseDto<T>(
    val page: Int = 1,
    val results: List<T> = emptyList(),
    @param:Json(name = "total_pages")
    val totalPages: Int = 0,
    @param:Json(name = "total_results")
    val totalResults: Int = 0
)