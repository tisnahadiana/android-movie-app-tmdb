package com.deeromptech.androidmovieapptmdb.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.deeromptech.androidmovieapptmdb.core.common.UiState
import com.deeromptech.androidmovieapptmdb.domain.model.Video

@Composable
fun TrailerSection(
    trailerState: UiState<Video>,
    onTrailerClick: (String) -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Trailer",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        when (trailerState) {
            UiState.Loading -> {
                TrailerLoadingCard()
            }

            UiState.Empty -> {
                TrailerMessageCard(
                    title = "Trailer not available",
                    message = "No YouTube trailer is available for this movie."
                )
            }

            is UiState.Error -> {
                TrailerErrorCard(
                    message = trailerState.message,
                    onRetryClick = onRetryClick
                )
            }

            is UiState.Success -> {
                TrailerSuccessCard(
                    trailer = trailerState.data,
                    onTrailerClick = onTrailerClick
                )
            }
        }
    }
}

@Composable
private fun TrailerLoadingCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            CircularProgressIndicator()

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Loading trailer..."
            )
        }
    }
}

@Composable
private fun TrailerMessageCard(
    title: String,
    message: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun TrailerErrorCard(
    message: String,
    onRetryClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Failed to load trailer",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            OutlinedButton(
                onClick = onRetryClick
            ) {
                Text(
                    text = "Retry"
                )
            }
        }
    }
}

@Composable
private fun TrailerSuccessCard(
    trailer: Video,
    onTrailerClick: (String) -> Unit
) {
    val youtubeUrl = trailer.youtubeUrl

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = trailer.name.ifBlank { "YouTube Trailer" },
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = if (trailer.official) {
                    "Official ${trailer.type}"
                } else {
                    trailer.type.ifBlank { "Video" }
                },
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            if (youtubeUrl.isNullOrBlank()) {
                Text(
                    text = "Trailer link is not available.",
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                Button(
                    onClick = {
                        onTrailerClick(youtubeUrl)
                    }
                ) {
                    Text(
                        text = "Watch on YouTube"
                    )
                }
            }
        }
    }
}