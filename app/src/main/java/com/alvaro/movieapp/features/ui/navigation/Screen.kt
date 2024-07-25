package com.alvaro.movieapp.features.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen(Route.Home.name)
    object Search : Screen(Route.Search.name)
    object Detail : Screen("${Route.Detail.name}/{movieId}") {
        fun createRoute(movieId: Int) = "${Route.Detail.name}/$movieId"
    }

    object Favorite : Screen(Route.Favorite.name)
}