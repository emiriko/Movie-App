package com.alvaro.movieapp.core.di

import com.alvaro.movieapp.core.data.source.MovieRepository
import com.alvaro.movieapp.core.domain.repository.IMovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(repository: MovieRepository): IMovieRepository
}