package com.alvaro.movieapp.core.domain.usecase

import androidx.paging.PagingData
import com.alvaro.movieapp.core.domain.model.Movie
import com.alvaro.movieapp.core.domain.model.Review
import com.alvaro.movieapp.core.domain.repository.IMovieRepository
import com.alvaro.movieapp.core.presentation.state.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(
    private val repository: IMovieRepository
): MovieUseCase {
    override suspend fun getNowPlayingMoviesUseCase(): Flow<PagingData<Movie>> = repository.getNowPlayingMovies()

    override suspend fun getPopularMoviesUseCase(): Flow<PagingData<Movie>> = repository.getPopularMovies()

    override suspend fun getTopRatedMoviesUseCase(): Flow<PagingData<Movie>> = repository.getTopRatedMovies()

    override suspend fun getUpcomingMoviesUseCase(): Flow<PagingData<Movie>> = repository.getUpcomingMovies()

    override suspend fun getFavoritedMoviesUseCase(): Flow<Resource<List<Movie>>> = repository.getFavoritedMovies()
    
    override suspend fun getMovieInformation(movieId: Int): Flow<Resource<Movie>> = repository.getMovieDetail(movieId)

    override suspend fun getMovieReviews(movieId: Int): Flow<Resource<List<Review>>> = repository.getMovieReviews(movieId)

    override suspend fun getSearchResult(query: String): Flow<Resource<List<Movie>>> = repository.getSearchResult(query)
    
    override suspend fun updateMovieState(movie: Movie, newState: Boolean) = repository.updateMovieState(movie, newState)
}