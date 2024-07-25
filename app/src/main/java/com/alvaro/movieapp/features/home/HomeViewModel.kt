package com.alvaro.movieapp.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvaro.movieapp.core.domain.model.Movie
import com.alvaro.movieapp.core.domain.usecase.MovieUseCase
import com.alvaro.movieapp.core.presentation.state.MovieState
import com.alvaro.movieapp.core.presentation.state.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
): ViewModel() {
    
    val _movieState = MutableStateFlow(MovieState())
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
        useCase: suspend () -> Flow<Resource<List<Movie>>>,
        updateState: (Resource<List<Movie>>) -> Unit
    ) {
        viewModelScope.launch {
            updateState(Resource.Loading())
            useCase().collectLatest { result ->
                updateState(result)
            }
        }
    }

    private suspend fun getNowPlayingMoviesUseCase() = movieUseCase.getNowPlayingMoviesUseCase()
    private suspend fun getPopularMoviesUseCase() = movieUseCase.getPopularMoviesUseCase()
    private suspend fun getTopRatedMoviesUseCase() = movieUseCase.getTopRatedMoviesUseCase()
    private suspend fun getUpcomingMoviesUseCase() = movieUseCase.getUpcomingMoviesUseCase()

    private fun updateNowPlayingMoviesState(resource: Resource<List<Movie>>) {
        _movieState.value = _movieState.value.copy(nowPlayingMoviesState = resource)
    }

    private fun updatePopularMoviesState(resource: Resource<List<Movie>>) {
        _movieState.value = _movieState.value.copy(popularMoviesState = resource)
    }

    private fun updateTopRatedMoviesState(resource: Resource<List<Movie>>) {
        _movieState.value = _movieState.value.copy(topRatedMoviesState = resource)
    }

    private fun updateUpcomingMoviesState(resource: Resource<List<Movie>>) {
        _movieState.value = _movieState.value.copy(getUpcomingMoviesState = resource)
    }
}