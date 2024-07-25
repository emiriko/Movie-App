package com.alvaro.movieapp.features.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.alvaro.movieapp.core.domain.model.Movie
import com.alvaro.movieapp.core.presentation.state.Resource
import com.alvaro.movieapp.features.ui.composables.LoadingIndicator
import com.alvaro.movieapp.features.ui.composables.MovieInformation
import com.alvaro.movieapp.features.ui.composables.MovieNotFound
import com.alvaro.movieapp.features.ui.composables.PagingResourceHandler
import com.alvaro.movieapp.features.ui.composables.ResourceHandler
import com.alvaro.movieapp.features.ui.composables.SearchBar
import com.alvaro.movieapp.features.ui.theme.MovieAppTheme
import com.alvaro.movieapp.utils.Helper
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchScreen(
    state: LazyPagingItems<Movie>,
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
        PagingResourceHandler(
            resource = state,
            content = { movies, appendState ->
                if (movies.itemCount > 0) {
                    LazyColumn(
                        modifier = Modifier
                            .constrainAs(content) {
                                top.linkTo(searchBar.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                        contentPadding = PaddingValues(vertical = 12.dp),
                    ) {
                        items(movies.itemCount) { index ->
                            val movie = movies[index] ?: return@items
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
                        item { 
                            appendState()
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
            },
            loadingContent = {
                if (state.loadState.isIdle && state.itemCount == 0) {
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
                } else {
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
            state = flowOf(PagingData.from(movies)).collectAsLazyPagingItems(),
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
            state = flowOf(PagingData.empty<Movie>()).collectAsLazyPagingItems(),
            searchInput = TextFieldValue(""),
            onSearchInputChanged = {},
            onItemClicked = {},
        )
    }
}