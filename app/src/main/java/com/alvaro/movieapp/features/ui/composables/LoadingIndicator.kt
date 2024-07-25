package com.alvaro.movieapp.features.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ehsanmsz.mszprogressindicator.progressindicator.BallClipRotateProgressIndicator

@Composable
fun LoadingIndicator(
    backgroundColor: Color = Color.Transparent,
    loadingColor: Color = Color.LightGray,
    diameter: Dp = 60.dp,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        BallClipRotateProgressIndicator(
            color = loadingColor,
            minDiameter = diameter,
            maxDiameter = diameter,
            modifier = Modifier
                .background(backgroundColor)
        )
    }
}