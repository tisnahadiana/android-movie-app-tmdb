package com.deeromptech.androidmovieapptmdb.presentation.movieList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.deeromptech.androidmovieapptmdb.domain.model.Movie
import com.deeromptech.androidmovieapptmdb.domain.usecase.GetMoviesByGenreUseCase
import com.deeromptech.androidmovieapptmdb.presentation.navigation.MovieListRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class MovieListViewModel @Inject constructor(
    getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val route: MovieListRoute = savedStateHandle.toRoute()

    val genreId: Int = route.genreId
    val genreName: String = route.genreName

    val movies: Flow<PagingData<Movie>> = getMoviesByGenreUseCase(
        genreId = genreId
    ).cachedIn(viewModelScope)
}