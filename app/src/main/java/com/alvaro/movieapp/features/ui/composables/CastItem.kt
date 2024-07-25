package com.alvaro.movieapp.features.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.alvaro.movieapp.features.ui.theme.MovieAppTheme
import com.alvaro.movieapp.utils.getTMDBImageURL

@Composable
fun CastItem (
    image: String,
    name: String
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Image(
            image = image.getTMDBImageURL(),
            contentDescription = "Picture of $name",
            diameter = 50.dp,
            errorIcon = 50.dp,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )
        Text(
            text = name,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun CastItemPreview() {
    MovieAppTheme {
        CastItem(
            image = "https://image.tmdb.org/t/p/w500/6yoghtyfKucgX8k8rU3E9fXKTIQ.jpg",
            name = "Alvaro"
        )
    }
}