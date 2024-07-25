package com.alvaro.movieapp.core.presentation.state

import com.alvaro.movieapp.core.domain.model.Movie

data class MovieState (
    val nowPlayingMoviesState: Resource<List<Movie>> = Resource.Loading(),
    val popularMoviesState: Resource<List<Movie>> = Resource.Loading(),
    val topRatedMoviesState: Resource<List<Movie>> = Resource.Loading(),
    val getUpcomingMoviesState: Resource<List<Movie>> = Resource.Loading(),
)