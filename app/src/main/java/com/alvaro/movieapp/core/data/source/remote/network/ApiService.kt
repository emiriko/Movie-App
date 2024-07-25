package com.alvaro.movieapp.core.data.source.remote.network

import com.alvaro.movieapp.core.data.source.remote.response.ListMovieResponse
import com.alvaro.movieapp.core.data.source.remote.response.ListMovieReviewResponse
import com.alvaro.movieapp.core.data.source.remote.response.MovieDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int = 1,
    ): ListMovieResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1,
    ): ListMovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 1,
    ): ListMovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = 1,
    ): ListMovieResponse

    @GET("search/movie")
    suspend fun getSearchResult(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
    ): ListMovieResponse
    
    @GET("movie/{movieId}?append_to_response=credits")
    suspend fun getMovieDetail(
        @Path("movieId") id: Int,
    ): MovieDetailResponse
    
    @GET("movie/{movieId}/reviews")
    suspend fun getMovieReviews(
        @Path("movieId") id: Int,
    ) : ListMovieReviewResponse
}