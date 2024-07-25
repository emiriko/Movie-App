package com.alvaro.movieapp.utils

fun String.getTMDBImageURL(): String {
    return "https://image.tmdb.org/t/p/w500$this"
}

fun String.getTMDBOriginalImageURL(): String {
    return "https://image.tmdb.org/t/p/original$this"
}