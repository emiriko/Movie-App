package com.alvaro.movieapp.favorite

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.alvaro.movieapp.di.FavoriteModuleDependencies
import com.alvaro.movieapp.features.ui.composables.BottomNavigationBar
import com.alvaro.movieapp.features.ui.navigation.CustomTopAppBar
import com.alvaro.movieapp.features.ui.theme.MovieAppTheme
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteActivity : ComponentActivity() {
    @Inject
    lateinit var factory: ViewModelFactory

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val currentTitle = "Favorite"
            val state by favoriteViewModel.uiState.collectAsState()
            val context = LocalContext.current


            MovieAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            CustomTopAppBar(navController = navController, currentTitle)
                        },
                        bottomBar = {
                            BottomNavigationBar(
                                navController = navController,
                                useDeepLink = true
                            )
                        },
                        modifier = Modifier
                            .safeDrawingPadding()
                    ) { innerPadding ->
                        FavoriteScreen(
                            state = state,
                            onItemClicked = { movieId ->
                                val deepLinkIntent = Intent(Intent.ACTION_VIEW).apply {
                                    data = Uri.parse("movieapp://app/detail/$movieId")
                                }
                                deepLinkIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                context.startActivity(deepLinkIntent)
                            },
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}