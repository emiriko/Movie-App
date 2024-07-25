package com.alvaro.movieapp.features.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvaro.movieapp.features.ui.theme.MovieAppTheme
import io.eyram.iconsax.IconSax

@Composable
fun ErrorIndicator(
    message: String,
    onRetryClick: () -> Unit = {},
    retriable: Boolean = false,
    modifier: Modifier = Modifier,
    ) {
    Column (
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
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
        if (retriable) {
            OutlinedButton(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.errorContainer),
                onClick = onRetryClick,
            ) {
                Text(
                    text = "Retry",
                    color = MaterialTheme.colorScheme.errorContainer,
                )
            }
        }
    }
}

@Composable
fun ErrorMessage(
    error: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    orientation: Orientation = Orientation.Horizontal
) {
    val button: @Composable () -> Unit = {
        OutlinedButton(
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSurfaceVariant),
            onClick = onClick,
        ) {
            Text(
                text = "Retry",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }

    if (orientation == Orientation.Vertical) {
        Column(
            modifier = modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
            button()
        }
    } else {
        Row(
            modifier = modifier.padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f),
                maxLines = 2,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
            button()
        }
    }
}

@Preview
@Composable
fun ErrorIndicatorPreview() {
   MovieAppTheme {
         ErrorIndicator(
            message = "An error occurred",
             retriable = true
         )
   }
}