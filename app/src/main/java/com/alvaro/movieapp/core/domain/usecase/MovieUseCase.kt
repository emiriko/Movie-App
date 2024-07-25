package com.alvaro.movieapp.core.domain.usecase

import com.alvaro.movieapp.core.domain.model.Movie
import com.alvaro.movieapp.core.domain.model.Review
import com.alvaro.movieapp.core.presentation.state.Resource
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    suspend fun getNowPlayingMoviesUseCase(): Flow<Resource<List<Movie>>>
    suspend fun getPopularMoviesUseCase(): Flow<Resource<List<Movie>>>
    suspend fun getTopRatedMoviesUseCase(): Flow<Resource<List<Movie>>>
    suspend fun getUpcomingMoviesUseCase(): Flow<Resource<List<Movie>>>
    suspend fun getFavoritedMoviesUseCase(): Flow<Resource<List<Movie>>>
    suspend fun getMovieInformation(movieId: Int): Flow<Resource<Movie>>
    suspend fun getMovieReviews(movieId: Int): Flow<Resource<List<Review>>>
    suspend fun getSearchResult(query: String): Flow<Resource<List<Movie>>>
    suspend fun updateMovieState(movie: Movie, newState: Boolean)
}