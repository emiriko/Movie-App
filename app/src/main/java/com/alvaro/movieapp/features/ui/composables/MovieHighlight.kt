package com.alvaro.movieapp.features.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.SubcomposeAsyncImage
import com.alvaro.movieapp.features.ui.theme.LightBlue
import com.alvaro.movieapp.features.ui.theme.MovieAppTheme
import com.alvaro.movieapp.utils.getTMDBImageURL

@Composable
fun MovieHighlight(
    id: Int,
    movie: String,
    image: String,
    number: Int,
    onClickItem: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {
        Image(
            image = image.getTMDBImageURL(),
            contentDescription = movie,
            diameter = 60.dp,
            errorIcon = 60.dp,
            modifier = Modifier
                .height(210.dp)
                .width(150.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable { 
                    onClickItem(id)
                }
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(y = 36.dp, x = (-8).dp)
                .zIndex(2f),
        ) {
            Text(
                text = number.toString(),
                fontWeight = FontWeight.Bold,
                style = LocalTextStyle.current.merge(
                    TextStyle(
                        color = LightBlue,
                        fontSize = 80.sp,
                        drawStyle = Stroke(
                            width = 2f, 
                            join = StrokeJoin.Round,
                        )
                    ),
                ),
            )
        }
    }
}


@Preview
@Composable
fun MovieHighlightPreview() {
    MovieAppTheme {
        MovieHighlight(
            id = 1,
            movie = "The Matrix",
            image = "https://image.tmdb.org/t/p/w500/kf456ZqeC45XTvo6W9pW5clYKfQ.jpg",
            number = 1,
            onClickItem = {},
        )
    }
}