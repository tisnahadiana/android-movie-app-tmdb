package com.deeromptech.androidmovieapptmdb.data.mapper

import com.deeromptech.androidmovieapptmdb.core.common.Constants
import com.deeromptech.androidmovieapptmdb.data.remote.dto.MovieDetailDto
import com.deeromptech.androidmovieapptmdb.domain.model.MovieDetail

fun MovieDetailDto.toDomain(): MovieDetail {
    return MovieDetail(
        id = id,
        title = title.orEmpty(),
        overview = overview.orEmpty(),
        posterUrl = posterPath?.let { Constants.TMDB_IMAGE_BASE_URL + it },
        backdropUrl = backdropPath?.let { Constants.TMDB_IMAGE_BASE_URL + it },
        releaseDate = releaseDate.orEmpty(),
        runtime = runtime,
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,
        genres = genres.map { it.toDomain() },
        status = status.orEmpty(),
        tagline = tagline.orEmpty()
    )
}