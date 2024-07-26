package com.alvaro.movieapp.features.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvaro.movieapp.features.ui.theme.MovieAppTheme
import com.alvaro.movieapp.utils.getTMDBImageURL
import io.eyram.iconsax.IconSax

@Composable
fun MovieInformation(
    image: String,
    title: String,
    rating: Double,
    genres: List<String>,
    releaseDate: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(vertical = 12.dp, horizontal = 24.dp)
            .height(120.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            image = image.getTMDBImageURL(),
            contentDescription = title,
            diameter = 50.dp,
            errorIcon = 50.dp,
            modifier = Modifier
                .width(95.dp)
                .height(120.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                ShowcaseItem(
                    type = "Rating Icon",
                    value = rating.toString(),
                    icon = IconSax.Linear.Star1,
                    highlighted = true
                )
                ShowcaseItem(
                    type = "Genre Icon",
                    value = genres.joinToString(),
                    icon = IconSax.Linear.Ticket
                )
                ShowcaseItem(
                    type = "Calendar Icon",
                    value = releaseDate,
                    icon = IconSax.Linear.Calendar2
                )
            }
        }
    }
}

@Preview
@Composable
fun MovieInformationPreview() {
    MovieAppTheme {
        MovieInformation(
            image = "https://image.tmdb.org/t/p/w500/6Wdl9N6dL0Hi0T1qJLWSz6gMLbd.jpg",
            title = "Spiderman: No Way Home",
            rating = 8.5,
            genres = listOf("Action", "Adventure", "Science Fiction"),
            releaseDate = "2021-12-15"
        )
    }
}