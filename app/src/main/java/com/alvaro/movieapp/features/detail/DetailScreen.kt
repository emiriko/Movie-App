package com.alvaro.movieapp.features.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alvaro.movieapp.core.domain.model.Movie
import com.alvaro.movieapp.core.domain.model.Review
import com.alvaro.movieapp.core.presentation.state.MovieDetailState
import com.alvaro.movieapp.core.presentation.state.Resource
import com.alvaro.movieapp.features.ui.composables.CastItem
import com.alvaro.movieapp.features.ui.composables.Image
import com.alvaro.movieapp.features.ui.composables.ResourceHandler
import com.alvaro.movieapp.features.ui.composables.ReviewItem
import com.alvaro.movieapp.features.ui.composables.ShowcaseItem
import com.alvaro.movieapp.features.ui.composables.TabMenu
import com.alvaro.movieapp.features.ui.navigation.ProvideAppBarAction
import com.alvaro.movieapp.features.ui.navigation.ProvideAppBarTitle
import com.alvaro.movieapp.features.ui.theme.Highlight
import com.alvaro.movieapp.features.ui.theme.LightBlack
import com.alvaro.movieapp.features.ui.theme.MovieAppTheme
import com.alvaro.movieapp.features.ui.theme.Subtitle
import com.alvaro.movieapp.utils.Helper
import com.alvaro.movieapp.utils.getTMDBImageURL
import com.alvaro.movieapp.utils.getTMDBOriginalImageURL
import io.eyram.iconsax.IconSax

@Composable
fun DetailScreen(
    state: MovieDetailState,
    onFavoriteIconClicked: (movie: Movie, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val gridState = rememberLazyGridState()
    var tabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf("About Movie", "Reviews", "Cast")
    
    ResourceHandler(
        resource = state.movie,
        content = { movie ->
            ProvideAppBarTitle(movie.title)
            var isFavorite by remember {mutableStateOf(movie.isFavorite)}
            
            ProvideAppBarAction {
                IconButton(onClick = {
                    onFavoriteIconClicked(movie, !isFavorite)
                    isFavorite = !isFavorite
                }) {
                    Icon(
                        painter = painterResource(id = if(isFavorite) IconSax.Bold.Save2 else IconSax.Linear.Save2), 
                        contentDescription = if (isFavorite) "Unfavorite" else "Favorite",
                        tint = Color.White
                    )
                }
            }
            Column (
                modifier = modifier
                    .fillMaxSize(),
            ) {
                MovieBackground(
                    movieBackdrop = movie.image,
                    rating = movie.voteAverage
                )
                MovieHeader(
                    movieTitle = movie.title,
                    moviePoster = movie.image
                )
                Spacer(modifier = Modifier.height(16.dp))
                MovieDetail(
                    releaseDate = movie.releaseDate,
                    duration = movie.runtime,
                    genres = movie.genres.joinToString(),
                    modifier = Modifier
                        .offset(y = (-60).dp)
                )
                Spacer(modifier =  Modifier.height(24.dp))
                TabMenu(
                    tabs = tabs,
                    tabState = gridState,
                    tabIndex = tabIndex,
                    onTabClick = {idx ->
                        tabIndex = idx
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .offset(y = (-60).dp)
                )
                Spacer(modifier =  Modifier.height(24.dp))
                when(tabIndex) {
                    0 -> AboutSection(
                        overview = movie.overview,
                        modifier = Modifier
                            .offset(y = (-60).dp)
                    )
                    1 -> ReviewSection(
                        reviewState = state.reviews,
                        gridState = gridState,
                        modifier = Modifier
                            .offset(y = (-60).dp)
                    )
                    2 -> CastSection(
                        gridState = gridState,
                        movie = movie,
                        modifier = Modifier
                            .offset(y = (-60).dp)
                    )
                }
            }
        } 
    )
}

@Composable
fun MovieBackground(
    movieBackdrop: String,
    rating: Double,
    modifier: Modifier = Modifier
) {
    Box (
        modifier = modifier
            .fillMaxWidth()
    ){
        Image(
            image = movieBackdrop.getTMDBOriginalImageURL(),
            diameter = 50.dp,
            errorIcon = 50.dp,
            contentDescription = "Movie Background",
            modifier = Modifier
                .fillMaxWidth()
                .height(215.dp)
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(LightBlack.copy(alpha = 82F))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.5.dp)
            ) {
                Icon(
                    painter = painterResource(id = IconSax.Linear.Star1),
                    contentDescription = "Star Icon",
                    tint = Highlight,
                    modifier = Modifier
                        .size(16.dp)
                )
                Text(
                    text = "%.2f".format(rating),
                    color = Highlight,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
fun MovieHeader(
    movieTitle: String,
    moviePoster: String,
    modifier: Modifier = Modifier
) {
    Row (
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Bottom,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .offset(y = (-60).dp)
    ){
        Image(
            image = moviePoster.getTMDBImageURL(),
            contentDescription = "Movie Poster",
            diameter = 50.dp,
            errorIcon = 50.dp,
            modifier = Modifier
                .width(95.dp)
                .height(120.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Text(
            text = movieTitle,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            fontSize = 18.sp,
            lineHeight = 27.sp
        )
    }
}

@Composable
fun MovieDetail(
    releaseDate: String,
    duration: Int,
    genres: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ShowcaseItem(
            type = "release_date", 
            value = releaseDate, 
            icon = IconSax.Linear.Calendar2
        )
        Spacer(modifier =  Modifier.width(12.dp))
        VerticalDivider(
            color = Subtitle,
            thickness = 1.dp,
            modifier = Modifier
        )
        Spacer(modifier =  Modifier.width(12.dp))
        ShowcaseItem(
            type = "duration",
            value = "${duration} Minutes",
            icon = IconSax.Linear.Clock1
        )
        Spacer(modifier =  Modifier.width(12.dp))
        VerticalDivider(
            color = Subtitle,
            thickness = 1.dp,
            modifier = Modifier
        )
        Spacer(modifier =  Modifier.width(12.dp))
        ShowcaseItem(
            type = "genres",
            value = genres,
            icon = IconSax.Linear.Ticket
        )
        Spacer(modifier =  Modifier.width(12.dp))
    }
}

@Composable
fun AboutSection(
    overview: String,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = overview,
            color = Color.White,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}

@Composable
fun ReviewSection(
    reviewState: Resource<List<Review>>,
    gridState: LazyGridState,
    modifier: Modifier = Modifier
) {
    ResourceHandler(
        resource = reviewState,
        content = { reviews ->
            LazyVerticalGrid (
                columns = GridCells.Fixed(1),
                modifier = modifier,
                contentPadding = PaddingValues(horizontal = 16.dp),
                state = gridState,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                items(reviews) { review ->
                    ReviewItem(
                        image = review.image,
                        rating = review.rating,
                        name = review.subject,
                        review = review.review
                    )
                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun CastSection(
    movie: Movie,
    gridState: LazyGridState,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(vertical = 28.dp, horizontal = 16.dp)
    ) {
        items(movie.casts) { cast ->
            CastItem(
                image = cast.image, 
                name = cast.name
            )
        }
    }
}
        
@Preview
@Composable
fun DetailScreenPreview() {
    MovieAppTheme {
        val movies = Helper.getMovies()
        val reviews = Helper.getReviews()
        DetailScreen(
            state = MovieDetailState(
                movie = Resource.Success(movies.first()),
                reviews = Resource.Success(reviews),
            ),

            onFavoriteIconClicked = {
                    _, _ -> Unit
            }
        )
    }
}

@Preview
@Composable
fun DetailScreenLoadingPreview() {
    MovieAppTheme {
        val movies = Helper.getMovies()
        DetailScreen(
            state = MovieDetailState(
                movie = Resource.Success(movies.first()),
                reviews = Resource.Loading()
            ),
            onFavoriteIconClicked = {
                _, _ ->
            }
        )
    }
}