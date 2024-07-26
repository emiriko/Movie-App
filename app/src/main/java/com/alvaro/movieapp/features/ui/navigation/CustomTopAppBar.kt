package com.alvaro.movieapp.features.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.referentialEqualityPolicy
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.LocalOwnersProvider
import com.alvaro.movieapp.features.ui.theme.LightWhite
import io.eyram.iconsax.IconSax

@Composable
fun ProvideAppBarAction(actions: @Composable RowScope.() -> Unit) {
    if (LocalViewModelStoreOwner.current == null || LocalViewModelStoreOwner.current !is NavBackStackEntry)
        return
    val actionViewModel = viewModel(initializer = { TopAppBarViewModel() })

    SideEffect {
        actionViewModel.actionState = actions
    }
}

@Composable
fun ProvideAppBarTitle(title: String) {
    if (LocalViewModelStoreOwner.current == null || LocalViewModelStoreOwner.current !is NavBackStackEntry)
        return
    val actionViewModel = viewModel(initializer = { TopAppBarViewModel() })

    actionViewModel.titleState = title
}

@Composable
fun RowScope.AppBarAction(navBackStackEntry: NavBackStackEntry?) {
    val stateHolder = rememberSaveableStateHolder()
    navBackStackEntry?.LocalOwnersProvider(stateHolder) {
        val actionViewModel = viewModel(initializer = { TopAppBarViewModel() })

        actionViewModel.actionState?.let { it() }
    }
}


@Composable
fun AppBarTitle(title: String) {
    val actionViewModel = viewModel(initializer = { TopAppBarViewModel() })

    actionViewModel.titleState = title

    Text(
        text = actionViewModel.titleState,
        color = LightWhite,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.ExtraBold,
        textAlign = TextAlign.Center,
        modifier = Modifier,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}


private class TopAppBarViewModel : ViewModel() {
    var titleState by mutableStateOf("Movies")
    var actionState by mutableStateOf(
        null as (@Composable RowScope.() -> Unit)?,
        referentialEqualityPolicy()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(navController: NavHostController, title: String?) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            if (
                navController.previousBackStackEntry != null
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        painter = painterResource(id = IconSax.Bold.ArrowLeft3),
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        },
        title = {
            AppBarTitle(title ?: "Movies")
        },
        actions = {
            AppBarAction(navController.currentBackStackEntry)
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}
