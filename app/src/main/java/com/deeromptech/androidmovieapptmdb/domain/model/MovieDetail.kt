package com.deeromptech.androidmovieapptmdb.domain.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val releaseDate: String,
    val runtime: Int?,
    val voteAverage: Double,
    val voteCount: Int,
    val genres: List<Genre>,
    val status: String,
    val tagline: String
)