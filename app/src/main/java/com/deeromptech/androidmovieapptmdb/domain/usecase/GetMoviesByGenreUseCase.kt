package com.deeromptech.androidmovieapptmdb.domain.usecase

import androidx.paging.PagingData
import com.deeromptech.androidmovieapptmdb.domain.model.Movie
import com.deeromptech.androidmovieapptmdb.domain.repository.MovieRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetMoviesByGenreUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    operator fun invoke(
        genreId: Int
    ): Flow<PagingData<Movie>> {
        return repository.getMoviesByGenre(genreId = genreId)
    }
}