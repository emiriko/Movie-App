package com.alvaro.movieapp.features.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.alvaro.movieapp.R
import com.alvaro.movieapp.features.ui.theme.MovieAppTheme
import com.alvaro.movieapp.features.ui.theme.Subtitle

@Composable
fun EmtpyFavorite(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            image = R.drawable.favorite_not_found,
            contentDescription = "Not Found",
            diameter = 30.dp,
            errorIcon = 30.dp,
            modifier = Modifier
                .size(76.dp)
        )
        Spacer(modifier =  Modifier.height(8.dp))
        Text(
            text = "There is No Movie Yet!",
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            color = Color.White
        )

        Text(
            text = "Mark your favorite movie to see it here!",
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            color = Subtitle
        )
    }
}

@Preview
@Composable
fun EmtpyFavoritePreview() {
    MovieAppTheme {
        EmtpyFavorite(
            modifier = Modifier.padding(16.dp)
        )
    }
}