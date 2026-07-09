package com.deeromptech.androidmovieapptmdb.data.remote.dto

data class MovieDetailDto(
    val id: Int,
    val title: String?,
    val overview: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val runtime: Int?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val genres: List<GenreDto> = emptyList(),
    val status: String?,
    val tagline: String?
)