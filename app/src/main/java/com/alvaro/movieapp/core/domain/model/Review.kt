package com.alvaro.movieapp.core.domain.model

data class Review (
    val review: String,
    val subject: String,
    val image: String,
    val rating: Double,
)