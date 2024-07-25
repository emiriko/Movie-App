package com.alvaro.movieapp.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.alvaro.movieapp.core.data.source.local.entity.CastEntity
import com.alvaro.movieapp.core.data.source.local.entity.MovieEntity
import com.alvaro.movieapp.core.data.source.local.entity.MovieWithCasts
import com.alvaro.movieapp.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies where movie_id = :movieId")
    fun getMovieById(movieId: Int): Flow<MovieWithCasts>
    
    @Query("SELECT * FROM movies where movie_id = :movieId")
    fun getMovieEntityById(movieId: Int): Flow<MovieEntity>
    
    @Query("SELECT * FROM movies where is_favorite = 1")
    fun getFavoriteMovies(): Flow<List<MovieEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movie: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCasts(casts: List<CastEntity>)
    
    @Update
    suspend fun updateDetailMovie(movie: MovieEntity)
}