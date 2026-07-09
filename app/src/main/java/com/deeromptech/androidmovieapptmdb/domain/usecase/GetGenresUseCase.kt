package com.deeromptech.androidmovieapptmdb.domain.usecase

import com.deeromptech.androidmovieapptmdb.domain.model.Genre
import com.deeromptech.androidmovieapptmdb.domain.repository.MovieRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val repository: MovieRepository
) {

    suspend operator fun invoke(): Result<List<Genre>> {
        return repository.getGenres()
    }
}