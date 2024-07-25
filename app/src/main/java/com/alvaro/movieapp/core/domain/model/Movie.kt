package com.alvaro.movieapp.core.domain.model

data class Movie (
    val id: Int,
    
    val overview: String,

    val title: String,

    val genres: List<String>,

    val image: String,

    val releaseDate: String,

    val voteAverage: Double, 
    
    val runtime: Int,
    
    val backdropImage: String,
    
    val isFavorite: Boolean,
    
    val casts: List<Cast>
)