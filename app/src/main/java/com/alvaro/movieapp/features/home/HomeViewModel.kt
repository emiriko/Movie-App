package com.alvaro.movieapp.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alvaro.movieapp.core.domain.model.Movie
import com.alvaro.movieapp.core.domain.usecase.MovieUseCase
import com.alvaro.movieapp.core.presentation.state.MovieState
import com.alvaro.movieapp.core.presentation.state.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
): ViewModel() {
    
    private val _movieState = MutableStateFlow(MovieState())
    val movieState
        get() = _movieState

    init {
        fetchMovies()
    }
    
    private fun fetchMovies() {
        fetchMovieData(::getNowPlayingMoviesUseCase, ::updateNowPlayingMoviesState)
        fetchMovieData(::getPopularMoviesUseCase, ::updatePopularMoviesState)
        fetchMovieData(::getTopRatedMoviesUseCase, ::updateTopRatedMoviesState)
        fetchMovieData(::getUpcomingMoviesUseCase, ::updateUpcomingMoviesState)
    }

    private fun fetchMovieData(
        useCase: suspend () -> Flow<PagingData<Movie>>,
        updateState: (Flow<PagingData<Movie>>) -> Unit
    ) {
        viewModelScope.launch {
            updateState(
                useCase()
                    .distinctUntilChanged()
                    .cachedIn(viewModelScope)
            )
        }
    }

    private suspend fun getNowPlayingMoviesUseCase() = movieUseCase.getNowPlayingMoviesUseCase()
    private suspend fun getPopularMoviesUseCase() = movieUseCase.getPopularMoviesUseCase()
    private suspend fun getTopRatedMoviesUseCase() = movieUseCase.getTopRatedMoviesUseCase()
    private suspend fun getUpcomingMoviesUseCase() = movieUseCase.getUpcomingMoviesUseCase()

    private fun updateNowPlayingMoviesState(resource: Flow<PagingData<Movie>>) {
        _movieState.update { it.copy(nowPlayingMoviesState = resource) }
    }

    private fun updatePopularMoviesState(resource: Flow<PagingData<Movie>>) {
        _movieState.update { it.copy(popularMoviesState = resource) }
    }

    private fun updateTopRatedMoviesState(resource: Flow<PagingData<Movie>>) {
        _movieState.update { it.copy(topRatedMoviesState = resource) }
    }

    private fun updateUpcomingMoviesState(resource: Flow<PagingData<Movie>>) {
        _movieState.update { it.copy(upcomingMoviesState = resource) }
    }
}