package com.alvaro.movieapp.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    var movieId: Int,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "genres")
    var genres: List<String>,

    @ColumnInfo(name = "image")
    var image: String,

    @ColumnInfo(name = "release_date")
    var releaseDate: String,

    @ColumnInfo(name = "vote_average")
    var voteAverage: Double,

    @ColumnInfo(name = "runtime")
    var runtime: Int = 0,

    @ColumnInfo(name = "backdrop_image")
    var backdropImage: String = "",

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false
)

data class MovieWithCasts(
    @Embedded
    val movie: MovieEntity,

    @Relation(
        parentColumn = "movie_id",
        entityColumn = "movie_id"
    )
    val casts: List<CastEntity>
)
