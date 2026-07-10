package com.deeromptech.androidmovieapptmdb.presentation.movieDetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.deeromptech.androidmovieapptmdb.core.common.ErrorMessageMapper
import com.deeromptech.androidmovieapptmdb.core.common.UiState
import com.deeromptech.androidmovieapptmdb.domain.model.MovieDetail
import com.deeromptech.androidmovieapptmdb.domain.model.Review
import com.deeromptech.androidmovieapptmdb.domain.model.Video
import com.deeromptech.androidmovieapptmdb.presentation.component.AppendErrorItem
import com.deeromptech.androidmovieapptmdb.presentation.component.AppendLoadingItem
import com.deeromptech.androidmovieapptmdb.presentation.component.EmptyView
import com.deeromptech.androidmovieapptmdb.presentation.component.ErrorView
import com.deeromptech.androidmovieapptmdb.presentation.component.LoadingView
import com.deeromptech.androidmovieapptmdb.presentation.component.ReviewItem
import com.deeromptech.androidmovieapptmdb.presentation.component.TrailerSection
import java.util.Locale

@Composable
fun MovieDetailScreen(
    onBackClick: () -> Unit,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val detailState by viewModel.detailState.collectAsStateWithLifecycle()
    val trailerState by viewModel.trailerState.collectAsStateWithLifecycle()
    val reviews = viewModel.reviews.collectAsLazyPagingItems()

    MovieDetailContent(
        detailState = detailState,
        trailerState = trailerState,
        reviews = reviews,
        onBackClick = onBackClick,
        onRetryDetailClick = viewModel::loadMovieDetail,
        onRetryTrailerClick = viewModel::loadTrailer,
        onTrailerClick = { youtubeUrl ->
            openUrl(
                context = context,
                url = youtubeUrl
            )
        }
    )
}

@Composable
private fun MovieDetailContent(
    detailState: UiState<MovieDetail>,
    trailerState: UiState<Video>,
    reviews: LazyPagingItems<Review>,
    onBackClick: () -> Unit,
    onRetryDetailClick: () -> Unit,
    onRetryTrailerClick: () -> Unit,
    onTrailerClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        MovieDetailHeader(
            onBackClick = onBackClick
        )

        when (detailState) {
            UiState.Loading -> {
                LoadingView(
                    modifier = Modifier.weight(1f),
                    message = "Loading movie detail..."
                )
            }

            UiState.Empty -> {
                EmptyView(
                    modifier = Modifier.weight(1f),
                    message = "Movie detail is not available."
                )
            }

            is UiState.Error -> {
                ErrorView(
                    modifier = Modifier.weight(1f),
                    message = detailState.message,
                    onRetryClick = onRetryDetailClick
                )
            }

            is UiState.Success -> {
                MovieDetailSuccessContent(
                    modifier = Modifier.weight(1f),
                    movieDetail = detailState.data,
                    trailerState = trailerState,
                    reviews = reviews,
                    onRetryTrailerClick = onRetryTrailerClick,
                    onTrailerClick = onTrailerClick
                )
            }
        }
    }
}

@Composable
private fun MovieDetailHeader(
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
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

        Text(
            text = "Movie Detail",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun MovieDetailSuccessContent(
    movieDetail: MovieDetail,
    trailerState: UiState<Video>,
    reviews: LazyPagingItems<Review>,
    onRetryTrailerClick: () -> Unit,
    onTrailerClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val bottomPadding = WindowInsets.navigationBars
        .asPaddingValues()
        .calculateBottomPadding() + 24.dp

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            bottom = bottomPadding
        )
    ) {
        item {
            MovieBackdrop(
                backdropUrl = movieDetail.backdropUrl,
                title = movieDetail.title
            )
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                MovieTitleSection(
                    movieDetail = movieDetail
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                MovieInfoCard(
                    movieDetail = movieDetail
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                MovieGenreSection(
                    movieDetail = movieDetail
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                MovieOverviewSection(
                    overview = movieDetail.overview
                )

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                TrailerSection(
                    trailerState = trailerState,
                    onTrailerClick = onTrailerClick,
                    onRetryClick = onRetryTrailerClick
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                ReviewsHeader()
            }
        }

        when (val refreshState = reviews.loadState.refresh) {
            is LoadState.Loading -> {
                item {
                    AppendLoadingItem()
                }
            }

            is LoadState.Error -> {
                item {
                    AppendErrorItem(
                        message = ErrorMessageMapper.map(refreshState.error),
                        onRetryClick = {
                            reviews.retry()
                        }
                    )
                }
            }

            is LoadState.NotLoading -> {
                if (reviews.itemCount == 0) {
                    item {
                        NoReviewsItem()
                    }
                } else {
                    items(
                        count = reviews.itemCount
                    ) { index ->
                        val review = reviews[index]

                        if (review != null) {
                            ReviewItem(
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 12.dp
                                ),
                                review = review
                            )
                        }
                    }

                    when (val appendState = reviews.loadState.append) {
                        is LoadState.Loading -> {
                            item {
                                AppendLoadingItem()
                            }
                        }

                        is LoadState.Error -> {
                            item {
                                AppendErrorItem(
                                    message = ErrorMessageMapper.map(appendState.error),
                                    onRetryClick = {
                                        reviews.retry()
                                    }
                                )
                            }
                        }

                        else -> Unit
                    }
                }
            }
        }
    }
}

@Composable
private fun MovieBackdrop(
    backdropUrl: String?,
    title: String
) {
    if (backdropUrl.isNullOrBlank()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No Backdrop Image",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    } else {
        AsyncImage(
            model = backdropUrl,
            contentDescription = title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun MovieTitleSection(
    movieDetail: MovieDetail
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        MoviePoster(
            posterUrl = movieDetail.posterUrl,
            title = movieDetail.title
        )

        Spacer(
            modifier = Modifier.width(16.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = movieDetail.title.ifBlank { "Untitled Movie" },
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            if (movieDetail.tagline.isNotBlank()) {
                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = movieDetail.tagline,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = movieDetail.releaseDate.toReleaseYearText(),
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = movieDetail.status.ifBlank { "Status unavailable" },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun MoviePoster(
    posterUrl: String?,
    title: String
) {
    if (posterUrl.isNullOrBlank()) {
        Box(
            modifier = Modifier
                .width(120.dp)
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "No Image",
                style = MaterialTheme.typography.bodySmall
            )
        }
    } else {
        AsyncImage(
            model = posterUrl,
            contentDescription = title,
            modifier = Modifier
                .width(120.dp)
                .aspectRatio(2f / 3f)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
private fun MovieInfoCard(
    movieDetail: MovieDetail
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            MovieInfoRow(
                label = "Rating",
                value = "${movieDetail.voteAverage.toRatingText()} / 10"
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            MovieInfoRow(
                label = "Vote Count",
                value = movieDetail.voteCount.toString()
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            MovieInfoRow(
                label = "Runtime",
                value = movieDetail.runtime.toRuntimeText()
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            MovieInfoRow(
                label = "Release Date",
                value = movieDetail.releaseDate.ifBlank { "Unknown" }
            )
        }
    }
}

@Composable
private fun MovieInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            modifier = Modifier.weight(1f),
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun MovieGenreSection(
    movieDetail: MovieDetail
) {
    if (movieDetail.genres.isEmpty()) {
        return
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Genres",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            movieDetail.genres.forEach { genre ->
                AssistChip(
                    onClick = {},
                    label = {
                        Text(
                            text = genre.name
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun MovieOverviewSection(
    overview: String
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Overview",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = overview.ifBlank { "Overview is not available." },
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun ReviewsHeader() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "User Reviews",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )
    }
}

@Composable
private fun NoReviewsItem() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "No reviews yet.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

private fun openUrl(
    context: Context,
    url: String
) {
    runCatching {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )

        context.startActivity(intent)
    }
}

private fun String.toReleaseYearText(): String {
    return if (length >= 4) {
        take(4)
    } else {
        "Unknown year"
    }
}

private fun Int?.toRuntimeText(): String {
    return if (this == null || this <= 0) {
        "Unknown"
    } else {
        "$this minutes"
    }
}

private fun Double.toRatingText(): String {
    return String.format(
        locale = Locale.US,
        format = "%.1f",
        this
    )
}