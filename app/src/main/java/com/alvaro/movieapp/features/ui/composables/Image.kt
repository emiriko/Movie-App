package com.alvaro.movieapp.features.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.alvaro.movieapp.R
import io.eyram.iconsax.IconSax

@Composable
fun Image(
    image: Any,
    contentDescription: String,
    diameter: Dp,
    errorIcon: Dp,
    contentScale: ContentScale = ContentScale.Crop,
    modifier: Modifier = Modifier
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .error(R.drawable.not_found)
            .build(),
        loading = {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingIndicator(
                    diameter = diameter,
                )
            }
        },
        error = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = IconSax.Linear.Image),
                    contentDescription = "Error Loading Image",
                    tint = Color.White,
                    modifier = Modifier
                        .size(errorIcon)
                )
            }
        },
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    )
}