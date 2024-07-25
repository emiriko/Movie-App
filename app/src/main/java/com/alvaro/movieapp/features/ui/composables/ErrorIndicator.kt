package com.alvaro.movieapp.features.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvaro.movieapp.features.ui.theme.MovieAppTheme
import io.eyram.iconsax.IconSax

@Composable
fun ErrorIndicator(
    modifier: Modifier = Modifier,
    message: String
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(
                id = IconSax.Linear.Danger
            ), 
            contentDescription = "Error Icon",
            tint = MaterialTheme.colorScheme.errorContainer,
        )
        Text(
            text = message,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.errorContainer
        )
    }
}

@Preview
@Composable
fun ErrorIndicatorPreview() {
   MovieAppTheme {
         ErrorIndicator(
            message = "An error occurred"
         )
   }
}