package com.alvaro.movieapp.features.ui.composables

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

@Composable
fun <T : Any> PagingResourceHandler(
    resource: LazyPagingItems<T>,
    content: @Composable (LazyPagingItems<T>, @Composable () -> Unit) -> Unit,
    modifier: Modifier = Modifier,
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
    errorContent: @Composable (String, () -> Unit) -> Unit = { error, onClick ->
        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ErrorIndicator(
                message = error,
                retriable = true,
                onRetryClick = onClick
            )
        }
    },
    loadingAppendContent: @Composable () -> Unit = {
        LoadingIndicator(
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    },
    errorAppendContent: @Composable (String, () -> Unit) -> Unit = { error, onClick ->
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            ErrorMessage(
                error = error,
                onClick = onClick,
                orientation = Orientation.Vertical
            )
        }
    }
) {
    resource.apply {
        when (loadState.refresh) {
            is LoadState.Loading -> {
                loadingContent()
            }

            is LoadState.Error -> {
                val error = resource.loadState.refresh as LoadState.Error
                errorContent(
                    error.error.localizedMessage ?: "An error occurred"
                ) { retry() }
            }

            else -> {
                content(resource) {
                    when (loadState.append) {
                        is LoadState.Loading -> {
                            loadingAppendContent()
                        }

                        is LoadState.Error -> {
                            val error = loadState.append as LoadState.Error
                            errorAppendContent(
                                error.error.localizedMessage ?: "An error occurred"
                            ) { retry() }
                        }

                        else -> {}
                    }
                }
            }
        }
    }

}