package com.deeromptech.androidmovieapptmdb.presentation.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deeromptech.androidmovieapptmdb.core.common.ErrorMessageMapper
import com.deeromptech.androidmovieapptmdb.core.common.UiState
import com.deeromptech.androidmovieapptmdb.domain.model.Genre
import com.deeromptech.androidmovieapptmdb.domain.usecase.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Genre>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Genre>>> = _uiState.asStateFlow()

    init {
        loadGenres()
    }

    fun loadGenres() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            getGenresUseCase()
                .onSuccess { genres ->
                    _uiState.value = if (genres.isEmpty()) {
                        UiState.Empty
                    } else {
                        UiState.Success(genres)
                    }
                }
                .onFailure { throwable ->
                    _uiState.value = UiState.Error(
                        message = ErrorMessageMapper.map(throwable)
                    )
                }
        }
    }
}