package com.alvaro.movieapp.core.domain.repository

import androidx.paging.PagingData
import com.alvaro.movieapp.core.domain.model.Movie
import com.alvaro.movieapp.core.domain.model.Review
import com.alvaro.movieapp.core.presentation.state.Resource
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    suspend fun getNowPlayingMovies(): Flow<PagingData<Movie>>
    suspend fun getPopularMovies(): Flow<PagingData<Movie>>
    suspend fun getTopRatedMovies(): Flow<PagingData<Movie>>
    suspend fun getUpcomingMovies(): Flow<PagingData<Movie>>
    suspend fun getMovieReviews(movieId: Int): Flow<PagingData<Review>>
    suspend fun getFavoritedMovies(): Flow<Resource<List<Movie>>>
    suspend fun getMovieDetail(id: Int): Flow<Resource<Movie>>
    suspend fun getSearchResult(query: String): Flow<PagingData<Movie>>
    suspend fun updateMovieState(movie: Movie, newState: Boolean)
}