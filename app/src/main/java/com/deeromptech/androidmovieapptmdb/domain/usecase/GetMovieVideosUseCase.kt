package com.deeromptech.androidmovieapptmdb.domain.usecase

import com.deeromptech.androidmovieapptmdb.domain.model.Video
import com.deeromptech.androidmovieapptmdb.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieVideosUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(
        movieId: Int
    ): Result<List<Video>> {
        return repository.getMovieVideos(movieId = movieId)
    }
}