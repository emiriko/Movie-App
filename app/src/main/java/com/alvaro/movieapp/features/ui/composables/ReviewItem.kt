package com.alvaro.movieapp.features.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alvaro.movieapp.features.ui.theme.LightBlue
import com.alvaro.movieapp.features.ui.theme.MovieAppTheme
import com.alvaro.movieapp.utils.Helper
import com.alvaro.movieapp.utils.getTMDBImageURL

@Composable
fun ReviewItem(
    name: String,
    review: String,
    image: String,
    rating: Double,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Image(
                image = image.getTMDBImageURL(),
                contentDescription = "Picture of $name",
                diameter = 22.dp,
                errorIcon = 22.dp,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
            )

            Text(
                text = "%.1f".format(rating),
                color = LightBlue,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleSmall,
                fontSize = 12.sp,
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall,
                fontSize = 12.sp,
                color = Color.White,
            )
            Text(
                text = review,
                style = MaterialTheme.typography.titleSmall,
                fontSize = 12.sp,
                color = Color.White,
            )
        }
    }
}

@Preview
@Composable
fun ReviewItemPreview() {
    val movie = Helper.getMovies().first()
    MovieAppTheme {
        ReviewItem(
            image = movie.image,
            rating = movie.voteAverage,
            name = "Alvaor Austin",
            review = "From DC Comics comes the Suicide Squad, " +
                    "an antihero team of incarcerated" +
                    " supervillains who act as deniable assets" +
                    " for the United States government.",
        )
    }
}