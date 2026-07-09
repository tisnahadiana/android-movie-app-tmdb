package com.deeromptech.androidmovieapptmdb.presentation.movie_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.deeromptech.androidmovieapptmdb.core.common.ErrorMessageMapper
import com.deeromptech.androidmovieapptmdb.core.common.UiState
import com.deeromptech.androidmovieapptmdb.domain.model.MovieDetail
import com.deeromptech.androidmovieapptmdb.domain.usecase.GetMovieDetailUseCase
import com.deeromptech.androidmovieapptmdb.presentation.navigation.MovieDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val route: MovieDetailRoute = savedStateHandle.toRoute()

    val movieId: Int = route.movieId

    private val _detailState = MutableStateFlow<UiState<MovieDetail>>(UiState.Loading)
    val detailState: StateFlow<UiState<MovieDetail>> = _detailState.asStateFlow()

    init {
        loadMovieDetail()
    }

    fun loadMovieDetail() {
        viewModelScope.launch {
            _detailState.value = UiState.Loading

            getMovieDetailUseCase(movieId = movieId)
                .onSuccess { movieDetail ->
                    _detailState.value = UiState.Success(movieDetail)
                }
                .onFailure { throwable ->
                    _detailState.value = UiState.Error(
                        message = ErrorMessageMapper.map(throwable)
                    )
                }
        }
    }
}