package com.alvaro.movieapp.core.presentation.state

import androidx.paging.PagingData
import com.alvaro.movieapp.core.domain.model.Movie
import com.alvaro.movieapp.core.domain.model.Review
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

data class MovieDetailState(
    val movie: Resource<Movie> = Resource.Loading(),
    val reviews: Flow<PagingData<Review>> = MutableStateFlow(PagingData.empty()),
)