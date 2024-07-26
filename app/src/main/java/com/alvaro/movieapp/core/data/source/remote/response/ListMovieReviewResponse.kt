package com.alvaro.movieapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListMovieReviewResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("total_pages")
    val totalPages: Int,

    @field:SerializedName("results")
    val results: List<ReviewItem>,

    @field:SerializedName("total_results")
    val totalResults: Int
)

data class AuthorDetails(

    @field:SerializedName("avatar_path")
    val avatarPath: String?,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("rating")
    val rating: Int?,

    @field:SerializedName("username")
    val username: String
)
