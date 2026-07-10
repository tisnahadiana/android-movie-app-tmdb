package com.deeromptech.androidmovieapptmdb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.deeromptech.androidmovieapptmdb.core.common.Constants
import com.deeromptech.androidmovieapptmdb.data.mapper.toDomain
import com.deeromptech.androidmovieapptmdb.data.remote.api.TmdbApi
import com.deeromptech.androidmovieapptmdb.data.remote.paging.MoviePagingSource
import com.deeromptech.androidmovieapptmdb.data.remote.paging.ReviewPagingSource
import com.deeromptech.androidmovieapptmdb.domain.model.Genre
import com.deeromptech.androidmovieapptmdb.domain.model.Movie
import com.deeromptech.androidmovieapptmdb.domain.model.MovieDetail
import com.deeromptech.androidmovieapptmdb.domain.model.Review
import com.deeromptech.androidmovieapptmdb.domain.model.Video
import com.deeromptech.androidmovieapptmdb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: TmdbApi
) : MovieRepository {

    override suspend fun getGenres(): Result<List<Genre>> {
        return runCatching {
            api.getMovieGenres()
                .genres
                .map { it.toDomain() }
                .filter { it.name.isNotBlank() }
        }
    }

    override fun getMoviesByGenre(
        genreId: Int
    ): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.DEFAULT_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource(
                    api = api,
                    genreId = genreId
                )
            }
        ).flow
    }

    override suspend fun getMovieDetail(
        movieId: Int
    ): Result<MovieDetail> {
        return runCatching {
            api.getMovieDetail(movieId = movieId).toDomain()
        }
    }

    override fun getMovieReviews(
        movieId: Int
    ): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.DEFAULT_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ReviewPagingSource(
                    api = api,
                    movieId = movieId
                )
            }
        ).flow
    }

    override suspend fun getMovieVideos(
        movieId: Int
    ): Result<List<Video>> {
        return runCatching {
            api.getMovieVideos(movieId = movieId)
                .results
                .map { it.toDomain() }
                .filter { it.key.isNotBlank() }
        }
    }
}