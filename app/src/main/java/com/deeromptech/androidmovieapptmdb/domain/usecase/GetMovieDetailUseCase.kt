package com.deeromptech.androidmovieapptmdb.domain.usecase

import com.deeromptech.androidmovieapptmdb.domain.model.MovieDetail
import com.deeromptech.androidmovieapptmdb.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(
        movieId: Int
    ): Result<MovieDetail> {
        return repository.getMovieDetail(movieId = movieId)
    }
}