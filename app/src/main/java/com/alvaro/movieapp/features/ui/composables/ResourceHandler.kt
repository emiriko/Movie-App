package com.alvaro.movieapp.features.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.alvaro.movieapp.core.presentation.state.Resource

@Composable
fun <T> ResourceHandler(
    resource: Resource<T>,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit,
    loadingContent: @Composable () -> Unit = {
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoadingIndicator(
                modifier = Modifier
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
    },
    errorContent: @Composable (String) -> Unit = { error ->
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ErrorIndicator(
                message = error,
                textAlign = TextAlign.Center
            )
        }
    }
) {
    when (resource) {
        is Resource.Success -> {
            content(resource.data)
        }

        is Resource.Loading -> {
            loadingContent()
        }

        is Resource.Error -> {
            errorContent(resource.error)
        }
    }
}