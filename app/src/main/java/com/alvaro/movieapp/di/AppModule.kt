package com.alvaro.movieapp.di

import com.alvaro.movieapp.core.di.RepositoryModule
import com.alvaro.movieapp.core.domain.usecase.MovieInteractor
import com.alvaro.movieapp.core.domain.usecase.MovieUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    
    @Binds
    @ViewModelScoped
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase
}