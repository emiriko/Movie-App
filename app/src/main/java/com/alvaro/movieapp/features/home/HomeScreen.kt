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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alvaro.movieapp.core.domain.model.Movie
import com.alvaro.movieapp.core.presentation.state.MovieState
import com.alvaro.movieapp.core.presentation.state.Resource
import com.alvaro.movieapp.features.ui.composables.LoadingIndicator
import com.alvaro.movieapp.features.ui.composables.MovieHighlight
import com.alvaro.movieapp.features.ui.theme.MovieAppTheme
import com.alvaro.movieapp.utils.Helper
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.alvaro.movieapp.features.ui.composables.ErrorIndicator
import com.alvaro.movieapp.features.ui.composables.Image
import com.alvaro.movieapp.features.ui.composables.ResourceHandler
import com.alvaro.movieapp.features.ui.composables.TabMenu
import com.alvaro.movieapp.utils.getTMDBImageURL

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
            popularMovies = movieState.popularMoviesState,
            onClickItem = onClickItem
        )
        Spacer(modifier = Modifier.height(64.dp))
        
        TabMenu(
            tabs = tabs,
            tabState = gridState,
            tabIndex = tabIndex,
            onTabClick = {idx ->
                tabIndex = idx
            }
        )
        
        Spacer(modifier =  Modifier.height(20.dp))
        
        CategoryMovies(
            state = when (tabIndex) {
                0 -> movieState.nowPlayingMoviesState
                1 -> movieState.popularMoviesState
                2 -> movieState.topRatedMoviesState
                3 -> movieState.getUpcomingMoviesState
                else -> Resource.Error("An error occurred")
            },
            gridState = gridState,
            onClickItem = onClickItem,
        )
    }
}

@Composable
fun CategoryMovies(
    state: Resource<List<Movie>>,
    gridState: LazyGridState,
    onClickItem: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {
        ResourceHandler(
            resource = state, 
            content = { movies ->
                LazyVerticalGrid(
                    state = gridState,
                    columns = GridCells.Adaptive(minSize = 105.dp),
                    verticalArrangement = Arrangement.spacedBy(18.dp),
                    horizontalArrangement = Arrangement.spacedBy(13.dp),
                    contentPadding = PaddingValues(bottom = 20.dp),
                ) {
                    items(movies) { movie ->
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
                }
            }
        )
    }
}

@Composable
fun MoviesHighlightLayout(
    title: String,
    state: Resource<List<Movie>>,
    onClickItem: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column (
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

        ResourceHandler(
            resource = state, 
            content = { movies ->
                LazyRow (
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(movies.size) { index ->
                        MovieHighlight(
                            id = movies[index].id,
                            movie = movies[index].title,
                            image = movies[index].image,
                            number =  index + 1,
                            onClickItem = onClickItem
                        )
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
            errorContent = {error ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentAlignment = Alignment.Center
                ) {
                    ErrorIndicator(
                        message = error
                    )
                }
            }
        )
    }
}

@Composable
fun PopularMovie(
    popularMovies: Resource<List<Movie>>,
    modifier: Modifier = Modifier,
    onClickItem: (Int) -> Unit,
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ){
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
                nowPlayingMoviesState = Resource.Success(Helper.getMovies()),
                popularMoviesState = Resource.Success(Helper.getMovies()),
                topRatedMoviesState = Resource.Success(Helper.getMovies()),
                getUpcomingMoviesState = Resource.Success(Helper.getMovies()),
            ),
            onClickItem = {},
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

@Preview
@Composable
fun HomeScreenLoadingPreview() {
    MovieAppTheme {
        HomeScreen(
            movieState = MovieState(
                nowPlayingMoviesState = Resource.Loading(),
                popularMoviesState = Resource.Loading(),
                topRatedMoviesState = Resource.Loading(),
                getUpcomingMoviesState = Resource.Loading(),
            ),
            onClickItem = {},
            modifier = Modifier
                .padding(16.dp)
        )
    }
}

@Preview
@Composable
fun HomeScreenErrorPreview() {
    MovieAppTheme {
        HomeScreen(
            movieState = MovieState(
                nowPlayingMoviesState = Resource.Error("An error occurred"),
                popularMoviesState = Resource.Error("An error occurred"),
                topRatedMoviesState = Resource.Error("An error occurred"),
                getUpcomingMoviesState = Resource.Error("An error occurred"),
            ),
            onClickItem = {},
            modifier = Modifier
                .padding(16.dp)
        )
    }
}