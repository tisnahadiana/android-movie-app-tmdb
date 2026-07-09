package com.deeromptech.androidmovieapptmdb.presentation.genre

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.deeromptech.androidmovieapptmdb.core.common.UiState
import com.deeromptech.androidmovieapptmdb.domain.model.Genre
import com.deeromptech.androidmovieapptmdb.presentation.component.EmptyView
import com.deeromptech.androidmovieapptmdb.presentation.component.ErrorView
import com.deeromptech.androidmovieapptmdb.presentation.component.LoadingView

@Composable
fun GenreScreen(
    onGenreClick: (Genre) -> Unit,
    viewModel: GenreViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    GenreContent(
        uiState = uiState,
        onGenreClick = onGenreClick,
        onRetryClick = viewModel::loadGenres
    )
}

@Composable
private fun GenreContent(
    uiState: UiState<List<Genre>>,
    onGenreClick: (Genre) -> Unit,
    onRetryClick: () -> Unit
) {
    when (uiState) {
        UiState.Loading -> {
            LoadingView(
                message = "Loading movie genres..."
            )
        }

        UiState.Empty -> {
            EmptyView(
                message = "No movie genres available."
            )
        }

        is UiState.Error -> {
            ErrorView(
                message = uiState.message,
                onRetryClick = onRetryClick
            )
        }

        is UiState.Success -> {
            GenreListContent(
                genres = uiState.data,
                onGenreClick = onGenreClick
            )
        }
    }
}

@Composable
private fun GenreListContent(
    genres: List<Genre>,
    onGenreClick: (Genre) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Movie Genres",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = "Choose a genre to discover movies.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = genres,
                key = { genre -> genre.id }
            ) { genre ->
                GenreItem(
                    genre = genre,
                    onClick = {
                        onGenreClick(genre)
                    }
                )
            }
        }
    }
}

@Composable
private fun GenreItem(
    genre: Genre,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp)
            .clickable(
                onClick = onClick
            ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = genre.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}