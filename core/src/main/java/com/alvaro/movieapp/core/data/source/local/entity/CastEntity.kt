package com.alvaro.movieapp.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CastEntity(
    @PrimaryKey
    @ColumnInfo(name = "cast_id")
    var castId: Int,

    @ColumnInfo(name = "movie_id")
    var movieId: Int,

    @ColumnInfo(name = "actor_name")
    var actorName: String,

    @ColumnInfo(name = "actor_image")
    var actorImage: String,
)