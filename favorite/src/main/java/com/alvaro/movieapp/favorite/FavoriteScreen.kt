package com.alvaro.movieapp.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.alvaro.movieapp.core.domain.model.Movie
import com.alvaro.movieapp.core.presentation.state.Resource
import com.alvaro.movieapp.features.ui.composables.EmtpyFavorite
import com.alvaro.movieapp.features.ui.composables.MovieInformation
import com.alvaro.movieapp.features.ui.composables.ResourceHandler
import com.alvaro.movieapp.features.ui.theme.MovieAppTheme
import com.alvaro.movieapp.utils.Helper

@Composable
fun FavoriteScreen(
    state: Resource<List<Movie>>,
    onItemClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    ResourceHandler(
        resource = state,
        content = { movies ->
            ConstraintLayout(
                modifier = modifier
                    .fillMaxSize()
            ) {
                val (empty, content) = createRefs()
                if (movies.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .constrainAs(content) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                        contentPadding = PaddingValues(vertical = 12.dp),
                    ) {
                        items(movies) { movie ->
                            MovieInformation(
                                image = movie.image,
                                title = movie.title,
                                rating = movie.voteAverage,
                                genres = movie.genres,
                                releaseDate = movie.releaseDate,
                                modifier = Modifier
                                    .clickable {
                                        onItemClicked(movie.id)
                                    }
                            )
                        }
                    }
                } else {
                    EmtpyFavorite(
                        modifier = Modifier
                            .constrainAs(empty) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            }
                    )
                }
            }
        }
    )
}

@Preview()
@Composable
fun FavoriteScreenPreview() {
    val movies = Helper.getMovies()
    MovieAppTheme {
        FavoriteScreen(
            state = Resource.Success(movies),
            onItemClicked = {}
        )
    }
}

@Preview()
@Composable
fun FavoriteScreenEmptyPreview() {
    MovieAppTheme {
        FavoriteScreen(
            state = Resource.Success(emptyList()),
            onItemClicked = {}
        )
    }
}