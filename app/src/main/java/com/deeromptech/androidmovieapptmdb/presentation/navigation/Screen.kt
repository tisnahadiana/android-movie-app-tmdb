package com.deeromptech.androidmovieapptmdb.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object GenreRoute

@Serializable
data class MovieListRoute(
    val genreId: Int,
    val genreName: String
)

@Serializable
data class MovieDetailRoute(
    val movieId: Int
)