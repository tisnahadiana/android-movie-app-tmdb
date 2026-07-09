package com.deeromptech.androidmovieapptmdb.data.remote.dto

import com.squareup.moshi.Json

data class MovieDetailDto(
    val id: Int,
    val title: String?,
    val overview: String?,
    @param:Json(name = "poster_path")
    val posterPath: String?,
    @param:Json(name = "backdrop_path")
    val backdropPath: String?,
    @param:Json(name = "release_date")
    val releaseDate: String?,
    val runtime: Int?,
    @param:Json(name = "vote_average")
    val voteAverage: Double?,
    @param:Json(name = "vote_count")
    val voteCount: Int?,
    val genres: List<GenreDto> = emptyList(),
    val status: String?,
    val tagline: String?
)