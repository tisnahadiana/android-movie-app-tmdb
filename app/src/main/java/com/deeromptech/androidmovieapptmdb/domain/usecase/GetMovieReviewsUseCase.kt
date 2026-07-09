package com.deeromptech.androidmovieapptmdb.domain.usecase

import androidx.paging.PagingData
import com.deeromptech.androidmovieapptmdb.domain.model.Review
import com.deeromptech.androidmovieapptmdb.domain.repository.MovieRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetMovieReviewsUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    operator fun invoke(
        movieId: Int
    ): Flow<PagingData<Review>> {
        return repository.getMovieReviews(movieId = movieId)
    }
}