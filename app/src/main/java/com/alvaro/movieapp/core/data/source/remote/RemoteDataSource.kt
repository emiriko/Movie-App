package com.alvaro.movieapp.core.data.source.remote

import com.alvaro.movieapp.core.data.source.remote.network.ApiService
import com.alvaro.movieapp.core.data.source.remote.response.MovieDetailResponse
import com.alvaro.movieapp.core.data.source.remote.response.MovieItem
import com.alvaro.movieapp.core.data.source.remote.response.ReviewItem
import com.alvaro.movieapp.core.domain.model.Review
import com.alvaro.movieapp.core.presentation.state.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {

    private suspend inline fun <T, R> fetchMovies(
        crossinline apiCall: suspend () -> T,
        crossinline transform: (T) -> R
    ): Flow<Resource<R>> = flow {
        val result = try {
            val response = apiCall()
            val data = transform(response)
            Resource.Success(data)
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        } catch (e: IOException) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
        emit(result)
    }
    
    suspend fun getNowPlayingMovies(
        page: Int
    ): Flow<Resource<List<MovieItem>>> {
        return fetchMovies(
            apiCall = { apiService.getNowPlayingMovies(page) },
            transform = { it.results }
        )
    }


    suspend fun getPopularMovies(
        page: Int
    ): Flow<Resource<List<MovieItem>>> {
        return fetchMovies(
            apiCall = { apiService.getPopularMovies(page) },
            transform = { it.results }
        )
    }

    suspend fun getTopRatedMovies(
        page: Int
    ): Flow<Resource<List<MovieItem>>> {
        return fetchMovies(
            apiCall = { apiService.getTopRatedMovies(page) },
            transform = { it.results }
        )
    }

    suspend fun getUpcomingMovies(
        page: Int
    ): Flow<Resource<List<MovieItem>>> {
        return fetchMovies(
            apiCall = { apiService.getUpcomingMovies(page) },
            transform = { it.results }
        )
    }

    suspend fun getSearchResult(query: String, page: Int): Flow<Resource<List<MovieItem>>> {
        return fetchMovies(
            apiCall = { apiService.getSearchResult(query, page) },
            transform = { it.results }
        )
    }
    
    suspend fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetailResponse>> {
        return fetchMovies(
            apiCall = { apiService.getMovieDetail(movieId) },
            transform = { it }
        )
    }
    
    suspend fun getMovieReviews(movieId: Int, page: Int): Flow<Resource<List<ReviewItem>>> {
        return fetchMovies(
            apiCall = { apiService.getMovieReviews(movieId, page) },
            transform = { it.results }
        )
    }
}