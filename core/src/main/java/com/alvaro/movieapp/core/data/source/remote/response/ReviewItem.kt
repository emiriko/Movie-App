package com.alvaro.movieapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ReviewItem(
    @field:SerializedName("author_details")
    val authorDetails: AuthorDetails,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("author")
    val author: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("content")
    val content: String,

    @field:SerializedName("url")
    val url: String
)

