package com.deeromptech.androidmovieapptmdb.domain.repository

import androidx.paging.PagingData
import com.deeromptech.androidmovieapptmdb.domain.model.Genre
import com.deeromptech.androidmovieapptmdb.domain.model.Movie
import com.deeromptech.androidmovieapptmdb.domain.model.MovieDetail
import com.deeromptech.androidmovieapptmdb.domain.model.Review
import com.deeromptech.androidmovieapptmdb.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getGenres(): Result<List<Genre>>

    fun getMoviesByGenre(
        genreId: Int
    ): Flow<PagingData<Movie>>

    suspend fun getMovieDetail(
        movieId: Int
    ): Result<MovieDetail>

    fun getMovieReviews(
        movieId: Int
    ): Flow<PagingData<Review>>

    suspend fun getMovieVideos(
        movieId: Int
    ): Result<List<Video>>
}