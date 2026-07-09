package com.deeromptech.androidmovieapptmdb.presentation.movieDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.deeromptech.androidmovieapptmdb.core.common.ErrorMessageMapper
import com.deeromptech.androidmovieapptmdb.core.common.UiState
import com.deeromptech.androidmovieapptmdb.domain.model.MovieDetail
import com.deeromptech.androidmovieapptmdb.domain.model.Review
import com.deeromptech.androidmovieapptmdb.domain.model.Video
import com.deeromptech.androidmovieapptmdb.domain.usecase.GetMovieDetailUseCase
import com.deeromptech.androidmovieapptmdb.domain.usecase.GetMovieReviewsUseCase
import com.deeromptech.androidmovieapptmdb.domain.usecase.GetMovieVideosUseCase
import com.deeromptech.androidmovieapptmdb.presentation.navigation.MovieDetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getMovieVideosUseCase: GetMovieVideosUseCase,
    getMovieReviewsUseCase: GetMovieReviewsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val route: MovieDetailRoute = savedStateHandle.toRoute()

    val movieId: Int = route.movieId

    private val _detailState = MutableStateFlow<UiState<MovieDetail>>(UiState.Loading)
    val detailState: StateFlow<UiState<MovieDetail>> = _detailState.asStateFlow()

    private val _trailerState = MutableStateFlow<UiState<Video>>(UiState.Loading)
    val trailerState: StateFlow<UiState<Video>> = _trailerState.asStateFlow()

    val reviews: Flow<PagingData<Review>> = getMovieReviewsUseCase(
        movieId = movieId
    ).cachedIn(viewModelScope)

    init {
        loadMovieDetail()
        loadTrailer()
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

    fun loadTrailer() {
        viewModelScope.launch {
            _trailerState.value = UiState.Loading

            getMovieVideosUseCase(movieId = movieId)
                .onSuccess { videos ->
                    val trailer = videos.findBestYouTubeTrailer()

                    _trailerState.value = if (trailer == null) {
                        UiState.Empty
                    } else {
                        UiState.Success(trailer)
                    }
                }
                .onFailure { throwable ->
                    _trailerState.value = UiState.Error(
                        message = ErrorMessageMapper.map(throwable)
                    )
                }
        }
    }

    private fun List<Video>.findBestYouTubeTrailer(): Video? {
        return firstOrNull { video ->
            video.site.equals("YouTube", ignoreCase = true) &&
                    video.type.equals("Trailer", ignoreCase = true) &&
                    video.official &&
                    !video.youtubeUrl.isNullOrBlank()
        } ?: firstOrNull { video ->
            video.site.equals("YouTube", ignoreCase = true) &&
                    video.type.equals("Trailer", ignoreCase = true) &&
                    !video.youtubeUrl.isNullOrBlank()
        } ?: firstOrNull { video ->
            video.site.equals("YouTube", ignoreCase = true) &&
                    !video.youtubeUrl.isNullOrBlank()
        }
    }
}