package com.deeromptech.androidmovieapptmdb.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.deeromptech.androidmovieapptmdb.domain.model.Review
import java.util.Locale

@Composable
fun ReviewItem(
    review: Review,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ReviewHeader(
                review = review
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = review.content.ifBlank { "No review content." },
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 8,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun ReviewHeader(
    review: Review
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = review.author.ifBlank { review.username.ifBlank { "Anonymous" } },
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = review.createdAt.toReviewDateText(),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Text(
                text = review.rating.toRatingText(),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

private fun String.toReviewDateText(): String {
    return if (length >= 10) {
        take(10)
    } else {
        "Unknown date"
    }
}

private fun Double?.toRatingText(): String {
    return if (this == null) {
        "No rating"
    } else {
        String.format(
            locale = Locale.US,
            format = "Rating %.1f / 10",
            this
        )
    }
}