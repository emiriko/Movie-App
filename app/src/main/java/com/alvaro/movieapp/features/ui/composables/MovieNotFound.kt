package com.alvaro.movieapp.features.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.alvaro.movieapp.features.ui.theme.LightWhite
import com.alvaro.movieapp.features.ui.theme.MovieAppTheme
import com.alvaro.movieapp.features.ui.theme.Subtitle

@Composable
fun MovieNotFound(
    initial: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            image = R.drawable.not_found,
            contentDescription = "Not Found",
            diameter = 30.dp,
            errorIcon = 30.dp,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(76.dp)
        )
        Spacer(modifier =  Modifier.height(8.dp))
        Text(
            text = if (initial) "Search the movie you want!" else "We are sorry, we can't find the movie :(",
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            color = Color.White
        )

        Text(
            text = "Find your movie by typing the title, eg: 'The Matrix'",
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            color = Subtitle
        )
    }
}

@Preview
@Composable
fun MovieNotFoundPreview() {
    MovieAppTheme {
        MovieNotFound(
            modifier = Modifier.padding(16.dp)
        )
    }
}