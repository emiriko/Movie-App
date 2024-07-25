package com.alvaro.movieapp.features.ui.navigation

import com.alvaro.movieapp.R
import io.eyram.iconsax.IconSax

sealed class NavigationItem(val route: String, val icon: Int, val labelResId: Int) {
    data object Home : NavigationItem(
        route = Screen.Home.route,
        icon = IconSax.Linear.Home2,
        labelResId = R.string.home
    )

    data object Search : NavigationItem(
        route = Screen.Search.route,
        icon = IconSax.Linear.SearchNormal1,
        labelResId = R.string.search
    )

    data object Favorite : NavigationItem(
        route = Screen.Favorite.route,
        icon = IconSax.Linear.Star1,
        labelResId = R.string.favorite
    )

    companion object {
        val items = listOf(Home, Search, Favorite)
    }
}