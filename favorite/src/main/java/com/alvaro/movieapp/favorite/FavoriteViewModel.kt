package com.alvaro.movieapp.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alvaro.movieapp.core.domain.model.Movie
import com.alvaro.movieapp.core.domain.usecase.MovieUseCase
import com.alvaro.movieapp.core.presentation.state.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<Resource<List<Movie>>>(Resource.Loading())

    val uiState
        get() = _uiState

    init {
        getFavoritedMovies()
    }

    private fun getFavoritedMovies() {
        viewModelScope.launch {
            _uiState.value = Resource.Loading()
            movieUseCase.getFavoritedMoviesUseCase()
                .collectLatest { resource ->
                    _uiState.value = resource
                }
        }
    }
}