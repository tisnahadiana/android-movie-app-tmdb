package com.deeromptech.androidmovieapptmdb.data.mapper

import com.deeromptech.androidmovieapptmdb.core.common.Constants
import com.deeromptech.androidmovieapptmdb.data.remote.dto.MovieDto
import com.deeromptech.androidmovieapptmdb.domain.model.Movie

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title.orEmpty(),
        overview = overview.orEmpty(),
        posterUrl = posterPath?.let { Constants.TMDB_IMAGE_BASE_URL + it },
        backdropUrl = backdropPath?.let { Constants.TMDB_IMAGE_BASE_URL + it },
        releaseDate = releaseDate.orEmpty(),
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,
        genreIds = genreIds
    )
}