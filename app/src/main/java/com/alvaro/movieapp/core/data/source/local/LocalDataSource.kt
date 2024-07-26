package com.alvaro.movieapp.core.data.source.local

import com.alvaro.movieapp.core.data.source.local.entity.CastEntity
import com.alvaro.movieapp.core.data.source.local.entity.MovieEntity
import com.alvaro.movieapp.core.data.source.local.entity.MovieWithCasts
import com.alvaro.movieapp.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao
) {
    fun getAllMovies(): Flow<List<MovieEntity>> = movieDao.getAllMovies()

    fun getMovieById(movieId: Int): Flow<MovieWithCasts> = movieDao.getMovieById(movieId)

    fun getMovieEntityById(movieId: Int): Flow<MovieEntity> = movieDao.getMovieEntityById(movieId)

    fun getFavoriteMovies(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovies()

    suspend fun insertMovies(movies: List<MovieEntity>) = movieDao.insertMovies(movies)

    suspend fun insertCasts(casts: List<CastEntity>) = movieDao.insertCasts(casts)

    suspend fun insertMovie(movie: MovieEntity) = movieDao.insertMovie(movie)

    suspend fun setFavoriteMovie(movie: MovieEntity, newState: Boolean) {
        movie.isFavorite = newState
        movieDao.updateDetailMovie(movie)
    }
}