package com.alvaro.movieapp.features.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alvaro.movieapp.features.ui.theme.Highlight
import com.alvaro.movieapp.features.ui.theme.MovieAppTheme
import com.alvaro.movieapp.features.ui.theme.Subtitle
import io.eyram.iconsax.IconSax

@Composable
fun ShowcaseItem(
    type: String,
    value: String,
    icon: Int,
    highlighted: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.5.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = type,
            tint = if (highlighted) Highlight else Subtitle,
            modifier = Modifier
                .size(13.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = if (highlighted) Highlight else Subtitle,
        )
    }
}

@Preview
@Composable
fun ShowcaseItemHighlightedPreview() {
    MovieAppTheme {
        ShowcaseItem(
            type = "Rating",
            value = "8.5",
            icon = IconSax.Linear.Star1,
            highlighted = true
        )
    }
}

@Preview
@Composable
fun ShowcaseItemPreview() {
    MovieAppTheme {
        ShowcaseItem(
            type = "Rating",
            value = "8.5",
            icon = IconSax.Linear.Star1,
            highlighted = false
        )
    }
}