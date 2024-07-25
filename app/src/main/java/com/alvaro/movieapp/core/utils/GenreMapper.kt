package com.alvaro.movieapp.core.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class Genre(
    val id: Int,
    val name: String
)

val movieGenres = listOf(
    Genre(28, "Action"),
    Genre(12, "Adventure"),
    Genre(16, "Animation"),
    Genre(35, "Comedy"),
    Genre(80, "Crime"),
    Genre(99, "Documentary"),
    Genre(18, "Drama"),
    Genre(10751, "Family"),
    Genre(14, "Fantasy"),
    Genre(36, "History"),
    Genre(27, "Horror"),
    Genre(10402, "Music"),
    Genre(9648, "Mystery"),
    Genre(10749, "Romance"),
    Genre(878, "Science Fiction"),
    Genre(10770, "TV Movie"),
    Genre(53, "Thriller"),
    Genre(10752, "War"),
    Genre(37, "Western")
)

val tvGenres = listOf(
    Genre(10759, "Action & Adventure"),
    Genre(16, "Animation"),
    Genre(35, "Comedy"),
    Genre(80, "Crime"),
    Genre(99, "Documentary"),
    Genre(18, "Drama"),
    Genre(10751, "Family"),
    Genre(10762, "Kids"),
    Genre(9648, "Mystery"),
    Genre(10763, "News"),
    Genre(10764, "Reality"),
    Genre(10765, "Sci-Fi & Fantasy"),
    Genre(10766, "Soap"),
    Genre(10767, "Talk"),
    Genre(10768, "War & Politics"),
    Genre(37, "Western")
)

val combinedGenres = (movieGenres + tvGenres).associateBy { it.id }

object GenreMapper {
    fun getGenreNameById(id: Int): String {
        return combinedGenres[id]?.name ?: "Unknown"
    }
}

class GenreConverters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return Gson().toJson(list)
    }
}