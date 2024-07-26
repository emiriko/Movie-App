package com.alvaro.movieapp.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.alvaro.movieapp.core.domain.model.Movie
import com.alvaro.movieapp.core.presentation.state.MovieState
import com.alvaro.movieapp.features.ui.composables.ErrorIndicator
import com.alvaro.movieapp.features.ui.composables.Image
import com.alvaro.movieapp.features.ui.composables.LoadingIndicator
import com.alvaro.movieapp.features.ui.composables.MovieHighlight
import com.alvaro.movieapp.features.ui.composables.PagingResourceHandler
import com.alvaro.movieapp.features.ui.composables.TabMenu
import com.alvaro.movieapp.features.ui.theme.MovieAppTheme
import com.alvaro.movieapp.utils.Helper
import com.alvaro.movieapp.utils.getTMDBImageURL
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
    movieState: MovieState,
    onClickItem: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val gridState = rememberLazyGridState()
    var tabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf("Now Playing", "Popular", "Top Rated", "Upcoming")

    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        PopularMovie(
            popularMovies = movieState.popularMoviesState.collectAsLazyPagingItems(),
            onClickItem = onClickItem
        )
        Spacer(modifier = Modifier.height(64.dp))

        TabMenu(
            tabs = tabs,
            tabState = gridState,
            tabIndex = tabIndex,
            onTabClick = { idx ->
                tabIndex = idx
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        CategoryMovies(
            state = when (tabIndex) {
                0 -> movieState.nowPlayingMoviesState.collectAsLazyPagingItems()
                1 -> movieState.popularMoviesState.collectAsLazyPagingItems()
                2 -> movieState.topRatedMoviesState.collectAsLazyPagingItems()
                3 -> movieState.upcomingMoviesState.collectAsLazyPagingItems()
                else -> movieState.nowPlayingMoviesState.collectAsLazyPagingItems()
            },
            gridState = gridState,
            onClickItem = onClickItem,
        )
    }
}

@Composable
fun CategoryMovies(
    state: LazyPagingItems<Movie>,
    gridState: LazyGridState,
    onClickItem: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {
        PagingResourceHandler(
            resource = state,
            content = { items, appendState ->
                LazyVerticalGrid(
                    state = gridState,
                    columns = GridCells.Adaptive(minSize = 105.dp),
                    verticalArrangement = Arrangement.spacedBy(18.dp),
                    horizontalArrangement = Arrangement.spacedBy(13.dp),
                    contentPadding = PaddingValues(bottom = 20.dp),
                ) {
                    items(items.itemCount) { index ->
                        val movie = state[index] ?: return@items
                        Image(
                            image = movie.image.getTMDBImageURL(),
                            contentDescription = movie.title,
                            diameter = 50.dp,
                            errorIcon = 50.dp,
                            modifier = Modifier
                                .height(150.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .clickable {
                                    onClickItem(movie.id)
                                }
                        )
                    }
                    item {
                        Column(
                            modifier = Modifier.matchParentSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            appendState()
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun MoviesHighlightLayout(
    title: String,
    state: LazyPagingItems<Movie>,
    onClickItem: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        PagingResourceHandler(
            resource = state,
            content = { items, appendState ->
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(items.itemCount) { index ->
                        items[index]?.let {
                            MovieHighlight(
                                id = it.id,
                                movie = it.title,
                                image = it.image,
                                number = index + 1,
                                onClickItem = onClickItem
                            )
                        }
                    }
                    item {
                        appendState()
                    }
                }
            },
            loadingContent = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingIndicator(
                        modifier = Modifier
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            },
            errorContent = { error, onClick ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ErrorIndicator(
                        message = error,
                        retriable = true,
                        onRetryClick = onClick
                    )
                }
            },
            loadingAppendContent = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    LoadingIndicator(
                        modifier = Modifier
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
        )
    }
}

@Composable
fun PopularMovie(
    popularMovies: LazyPagingItems<Movie>,
    modifier: Modifier = Modifier,
    onClickItem: (Int) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        MoviesHighlightLayout(
            title = "What do you want to watch?",
            state = popularMovies,
            onClickItem = onClickItem
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    MovieAppTheme {
        HomeScreen(
            movieState = MovieState(
                nowPlayingMoviesState = flowOf(PagingData.from(Helper.getMovies())),
                popularMoviesState = flowOf(PagingData.from(Helper.getMovies())),
                topRatedMoviesState = flowOf(PagingData.from(Helper.getMovies())),
                upcomingMoviesState = flowOf(PagingData.from(Helper.getMovies())),
            ),
            onClickItem = {},
            modifier = Modifier
                .padding(16.dp)
        )
    }
}