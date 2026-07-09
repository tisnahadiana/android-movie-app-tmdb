package com.deeromptech.androidmovieapptmdb.data.remote.dto

data class MovieDto(
    val id: Int,
    val title: String?,
    val overview: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val genreIds: List<Int> = emptyList()
)