package com.alvaro.movieapp.core.domain.repository

import com.alvaro.movieapp.core.domain.model.Movie
import com.alvaro.movieapp.core.domain.model.Review
import com.alvaro.movieapp.core.presentation.state.Resource
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    suspend fun getNowPlayingMovies(): Flow<Resource<List<Movie>>>
    suspend fun getPopularMovies(): Flow<Resource<List<Movie>>>
    suspend fun getTopRatedMovies(): Flow<Resource<List<Movie>>>
    suspend fun getUpcomingMovies(): Flow<Resource<List<Movie>>>
    suspend fun getFavoritedMovies(): Flow<Resource<List<Movie>>>
    suspend fun getMovieDetail(id: Int): Flow<Resource<Movie>>
    suspend fun getMovieReviews(movieId: Int): Flow<Resource<List<Review>>>
    suspend fun getSearchResult(query: String): Flow<Resource<List<Movie>>>
    suspend fun updateMovieState(movie: Movie, newState: Boolean)
}