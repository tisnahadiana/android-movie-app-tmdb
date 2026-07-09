package com.deeromptech.androidmovieapptmdb.presentation.movieList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.deeromptech.androidmovieapptmdb.core.common.ErrorMessageMapper
import com.deeromptech.androidmovieapptmdb.domain.model.Movie
import com.deeromptech.androidmovieapptmdb.presentation.component.AppendErrorItem
import com.deeromptech.androidmovieapptmdb.presentation.component.AppendLoadingItem
import com.deeromptech.androidmovieapptmdb.presentation.component.EmptyView
import com.deeromptech.androidmovieapptmdb.presentation.component.ErrorView
import com.deeromptech.androidmovieapptmdb.presentation.component.LoadingView
import com.deeromptech.androidmovieapptmdb.presentation.component.MovieCard

@Composable
fun MovieListScreen(
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val movies = viewModel.movies.collectAsLazyPagingItems()

    MovieListContent(
        genreName = viewModel.genreName,
        movies = movies,
        onBackClick = onBackClick,
        onMovieClick = onMovieClick
    )
}

@Composable
private fun MovieListContent(
    genreName: String,
    movies: LazyPagingItems<Movie>,
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        MovieListHeader(
            genreName = genreName,
            onBackClick = onBackClick
        )

        MovieListBody(
            movies = movies,
            genreName = genreName,
            onMovieClick = onMovieClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun MovieListHeader(
    genreName: String,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = onBackClick
            ) {
                Text(
                    text = "Back"
                )
            }

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = genreName,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Discover movies by genre",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )
    }
}

@Composable
private fun MovieListBody(
    movies: LazyPagingItems<Movie>,
    genreName: String,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val refreshState = movies.loadState.refresh

    when {
        refreshState is LoadState.Loading -> {
            LoadingView(
                modifier = modifier,
                message = "Loading $genreName movies..."
            )
        }

        refreshState is LoadState.Error -> {
            ErrorView(
                modifier = modifier,
                message = ErrorMessageMapper.map(refreshState.error),
                onRetryClick = {
                    movies.retry()
                }
            )
        }

        movies.itemCount == 0 -> {
            EmptyView(
                modifier = modifier,
                message = "No movies found for $genreName."
            )
        }

        else -> {
            MovieGrid(
                modifier = modifier,
                movies = movies,
                onMovieClick = onMovieClick
            )
        }
    }
}

@Composable
private fun MovieGrid(
    movies: LazyPagingItems<Movie>,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 24.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            count = movies.itemCount
        ) { index ->
            val movie = movies[index]

            if (movie != null) {
                MovieCard(
                    movie = movie,
                    onClick = {
                        onMovieClick(movie.id)
                    }
                )
            }
        }

        when (val appendState = movies.loadState.append) {
            is LoadState.Loading -> {
                item(
                    span = {
                        GridItemSpan(maxLineSpan)
                    }
                ) {
                    AppendLoadingItem()
                }
            }

            is LoadState.Error -> {
                item(
                    span = {
                        GridItemSpan(maxLineSpan)
                    }
                ) {
                    AppendErrorItem(
                        message = ErrorMessageMapper.map(appendState.error),
                        onRetryClick = {
                            movies.retry()
                        }
                    )
                }
            }

            else -> Unit
        }
    }
}