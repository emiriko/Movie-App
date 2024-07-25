package com.alvaro.movieapp.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alvaro.movieapp.core.data.source.local.entity.CastEntity
import com.alvaro.movieapp.core.data.source.local.entity.MovieEntity
import com.alvaro.movieapp.core.utils.GenreConverters

@Database(
    entities = [MovieEntity::class, CastEntity::class], 
    version = 1, 
    exportSchema = false
)
@TypeConverters(GenreConverters::class)
abstract class MovieDatabase: RoomDatabase()  {
    abstract fun movieDao(): MovieDao
}