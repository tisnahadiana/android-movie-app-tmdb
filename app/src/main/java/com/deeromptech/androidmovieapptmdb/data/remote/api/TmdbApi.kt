package com.deeromptech.androidmovieapptmdb.data.remote.api

import com.deeromptech.androidmovieapptmdb.core.common.Constants
import com.deeromptech.androidmovieapptmdb.data.remote.dto.GenreResponseDto
import com.deeromptech.androidmovieapptmdb.data.remote.dto.MovieDetailDto
import com.deeromptech.androidmovieapptmdb.data.remote.dto.MovieDto
import com.deeromptech.androidmovieapptmdb.data.remote.dto.PagedResponseDto
import com.deeromptech.androidmovieapptmdb.data.remote.dto.ReviewDto
import com.deeromptech.androidmovieapptmdb.data.remote.dto.VideoResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("3/genre/movie/list")
    suspend fun getMovieGenres(
        @Query("language") language: String = Constants.DEFAULT_LANGUAGE
    ): GenreResponseDto

    @GET("3/discover/movie")
    suspend fun discoverMoviesByGenre(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = Constants.DEFAULT_LANGUAGE
    ): PagedResponseDto<MovieDto>

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = Constants.DEFAULT_LANGUAGE
    ): MovieDetailDto

    @GET("3/movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int,
        @Query("language") language: String = Constants.DEFAULT_LANGUAGE
    ): PagedResponseDto<ReviewDto>

    @GET("3/movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = Constants.DEFAULT_LANGUAGE
    ): VideoResponseDto
}