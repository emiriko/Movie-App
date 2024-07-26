package com.alvaro.movieapp.utils

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity

fun String.getTMDBImageURL(): String {
    return "https://image.tmdb.org/t/p/w500$this"
}

fun String.getTMDBOriginalImageURL(): String {
    return "https://image.tmdb.org/t/p/original$this"
}

fun Context.getActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}