package com.alvaro.movieapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @field:SerializedName("title")
    val title: String?,

    @field:SerializedName("backdrop_path")
    val backdropPath: String?,

    @field:SerializedName("credits")
    val credits: Credits?,

    @field:SerializedName("genres")
    val genres: List<GenresItem>,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("overview")
    val overview: String?,

    @field:SerializedName("runtime")
    val runtime: Int?,

    @field:SerializedName("poster_path")
    val posterPath: String?,

    @field:SerializedName("release_date")
    val releaseDate: String?,

    @field:SerializedName("vote_average")
    val voteAverage: Double?,
)

data class GenresItem(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int
)

data class Credits(
    @field:SerializedName("cast")
    val cast: List<CastItem>,
)

data class CastItem(

    @field:SerializedName("cast_id")
    val castId: Int,

    @field:SerializedName("character")
    val character: String,

    @field:SerializedName("gender")
    val gender: Int,

    @field:SerializedName("credit_id")
    val creditId: String,

    @field:SerializedName("known_for_department")
    val knownForDepartment: String,

    @field:SerializedName("original_name")
    val originalName: String,

    @field:SerializedName("popularity")
    val popularity: Any,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("profile_path")
    val profilePath: String?,

    @field:SerializedName("id")
    val id: Int,
)