package com.alvaro.movieapp.features

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import androidx.paging.compose.collectAsLazyPagingItems
import com.alvaro.movieapp.features.detail.DetailScreen
import com.alvaro.movieapp.features.detail.DetailViewModel
import com.alvaro.movieapp.features.home.HomeScreen
import com.alvaro.movieapp.features.home.HomeViewModel
import com.alvaro.movieapp.features.search.SearchScreen
import com.alvaro.movieapp.features.search.SearchViewModel
import com.alvaro.movieapp.features.ui.composables.BottomNavigationBar
import com.alvaro.movieapp.features.ui.navigation.CustomTopAppBar
import com.alvaro.movieapp.features.ui.navigation.ProvideAppBarTitle
import com.alvaro.movieapp.features.ui.navigation.Route
import com.alvaro.movieapp.features.ui.navigation.Screen
import com.alvaro.movieapp.features.ui.theme.MovieAppTheme

@Composable
fun MovieApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var currentTitle by remember { mutableStateOf(currentRoute) }

    LaunchedEffect(currentRoute) {
        currentTitle = if (currentRoute == Screen.Detail.route) {
            "Detail"
        } else {
            currentRoute
        }
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(navController = navController, currentTitle)
        },
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomNavigationBar(
                    navController = navController,
                )
            }
        },
        modifier = modifier
            .safeDrawingPadding(),
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700)
                )
            },
        ) {
            composable(
                route = Screen.Home.route,
                deepLinks = listOf(
                    navDeepLink { uriPattern = "movieapp://app/home" }
                )
            ) {
                ProvideAppBarTitle(currentTitle ?: "Home")

                val homeViewModel: HomeViewModel = hiltViewModel()
                val movieState by homeViewModel.movieState.collectAsState()

                HomeScreen(
                    movieState = movieState,
                    onClickItem = { movieId ->
                        navController.navigate(Screen.Detail.createRoute(movieId)) {
                            popUpTo(Screen.Home.route) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier
                        .padding(horizontal = 24.dp),
                )
            }

            composable(
                route = Screen.Search.route,
                deepLinks = listOf(
                    navDeepLink { uriPattern = "movieapp://app/search" }
                )
            ) {
                ProvideAppBarTitle(currentTitle ?: "Search")

                val searchViewModel: SearchViewModel = hiltViewModel()

                val searchInput by searchViewModel.searchInput.collectAsState()
                val searchState = searchViewModel.results.collectAsLazyPagingItems()

                SearchScreen(
                    state = searchState,
                    searchInput = searchInput,
                    onSearchInputChanged = { query ->
                        searchViewModel.updateSearchQuery(query)
                    },
                    onItemClicked = { movieId ->
                        navController.navigate(Screen.Detail.createRoute(movieId)) {
                            popUpTo(Screen.Search.route) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    },
                )
            }
            composable(
                route = "${Route.Detail.name}/{movieId}",
                arguments = listOf(
                    navArgument("movieId") { type = NavType.IntType }
                ),
                deepLinks = listOf(
                    navDeepLink { uriPattern = "movieapp://app/detail/{movieId}" }
                )
            ) {
                val detailViewModel: DetailViewModel = hiltViewModel()
                val detailMovieState by detailViewModel.movieDetailState.collectAsState()

                DetailScreen(
                    state = detailMovieState,
                    onFavoriteIconClicked = { movie, newState ->
                        detailViewModel.updateMovieState(movie, newState)
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun MovieAppPreview() {
    CompositionLocalProvider(
        LocalContext provides LocalContext.current
    ) {
        MovieAppTheme {
            MovieApp(

            )
        }
    }
}