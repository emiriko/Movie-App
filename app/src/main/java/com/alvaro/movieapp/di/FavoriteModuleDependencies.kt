package com.alvaro.movieapp.di

import com.alvaro.movieapp.core.domain.usecase.MovieInteractor
import com.alvaro.movieapp.core.domain.usecase.MovieUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {
    fun provideMovieUseCase(): MovieUseCase
}