package com.alvaro.movieapp.core.presentation.state

import androidx.paging.PagingData
import com.alvaro.movieapp.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

data class MovieState(
    val nowPlayingMoviesState: Flow<PagingData<Movie>> = MutableStateFlow(PagingData.empty()),
    val popularMoviesState: Flow<PagingData<Movie>> = MutableStateFlow(PagingData.empty()),
    val topRatedMoviesState: Flow<PagingData<Movie>> = MutableStateFlow(PagingData.empty()),
    val upcomingMoviesState: Flow<PagingData<Movie>> = MutableStateFlow(PagingData.empty()),
)