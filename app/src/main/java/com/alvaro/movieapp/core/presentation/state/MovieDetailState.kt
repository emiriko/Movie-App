package com.alvaro.movieapp.core.presentation.state

import com.alvaro.movieapp.core.domain.model.Movie
import com.alvaro.movieapp.core.domain.model.Review

data class MovieDetailState (
    val movie: Resource<Movie> = Resource.Loading(),
    val reviews: Resource<List<Review>> = Resource.Loading(),
)