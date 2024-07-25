package com.alvaro.movieapp.features.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.alvaro.movieapp.core.domain.model.Movie
import com.alvaro.movieapp.core.presentation.state.Resource
import com.alvaro.movieapp.features.ui.composables.MovieInformation
import com.alvaro.movieapp.features.ui.composables.MovieNotFound
import com.alvaro.movieapp.features.ui.composables.ResourceHandler
import com.alvaro.movieapp.features.ui.composables.SearchBar
import com.alvaro.movieapp.features.ui.theme.MovieAppTheme
import com.alvaro.movieapp.utils.Helper

@Composable
fun SearchScreen(
    state: Resource<List<Movie>>,
    searchInput: TextFieldValue,
    onSearchInputChanged: (TextFieldValue) -> Unit,
    onItemClicked: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    ConstraintLayout (
        modifier = modifier
            .fillMaxSize()
    ){
        val ( searchBar, notFound, content ) = createRefs()
        SearchBar(
            searchText = searchInput,
            onSearchTextChanged = {
                onSearchInputChanged(it)
            },
            modifier = Modifier
                .constrainAs(searchBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(24.dp),
        )
            ResourceHandler(
                resource = state, 
                content = {movies ->
                if (movies.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .constrainAs(content) {
                                top.linkTo(searchBar.bottom)
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
                    MovieNotFound(
                        initial = searchInput.text.isEmpty(),
                        modifier = Modifier
                            .constrainAs(notFound) {
                                top.linkTo(searchBar.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            }
                    )
                }
            }       
        )     
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    MovieAppTheme {
        val movies = Helper.getMovies()
        SearchScreen(
            state = Resource.Success(movies),
            searchInput = TextFieldValue(""),
            onSearchInputChanged = {},
            onItemClicked = {},
        )
    }
}

@Preview
@Composable
fun SearchScreenEmptyPreview() {
    MovieAppTheme {
        SearchScreen(
            state = Resource.Success(emptyList()),
            searchInput = TextFieldValue(""),
            onSearchInputChanged = {},
            onItemClicked = {},
        )
    }
}