package com.alvaro.movieapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieItem(
	@field:SerializedName("overview")
	val overview: String,
	
	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("genre_ids")
	val genreIds: List<Int>,

	@field:SerializedName("poster_path")
	val posterPath: String?,
	
	@field:SerializedName("release_date")
	val releaseDate: String?,
	
	@field:SerializedName("vote_average")
	val voteAverage: Double?,

	@field:SerializedName("id")
	val id: Int,
)
