package com.alvaro.movieapp.features.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.alvaro.movieapp.core.domain.model.Movie
import com.alvaro.movieapp.core.domain.model.Review
import com.alvaro.movieapp.core.domain.usecase.MovieUseCase
import com.alvaro.movieapp.core.presentation.state.MovieDetailState
import com.alvaro.movieapp.core.presentation.state.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val movieUseCase: MovieUseCase,
) : ViewModel() {
    private val movieId: Int? = savedStateHandle["movieId"]
    private val _movieDetailState = MutableStateFlow(MovieDetailState())
    val movieDetailState
        get() = _movieDetailState

    init {
        if (movieId != null) {
            getMovieDetail(movieId)
        } else {
            _movieDetailState.value = _movieDetailState.value.copy(
                movie = Resource.Error("Movie ID is null")
            )
        }
    }

    private fun <T> getData(
        movieId: Int,
        apiCall: suspend (Int) -> Flow<Resource<T>>,
        updateState: (Resource<T>) -> Unit
    ) {
        viewModelScope.launch {
            updateState(Resource.Loading())
            apiCall(movieId).collectLatest { data ->
                updateState(data)
            }
        }
    }

    private fun getMovieDetail(movieId: Int) {
        getData(
            movieId = movieId,
            apiCall = { getMovieInformation(movieId) },
            updateState = ::updateMovieInformationState
        )
        viewModelScope.launch {
            updateMovieReviewsState(
                getMovieReviews(movieId)
                    .distinctUntilChanged()
                    .cachedIn(viewModelScope)
            )
        }
    }

    fun updateMovieState(movie: Movie, newState: Boolean) {
        viewModelScope.launch {
            movieUseCase.updateMovieState(movie, newState)
        }
    }

    private suspend fun getMovieInformation(movieId: Int) =
        movieUseCase.getMovieInformation(movieId)

    private suspend fun getMovieReviews(movieId: Int) = movieUseCase.getMovieReviews(movieId)

    private fun updateMovieInformationState(resource: Resource<Movie>) {
        _movieDetailState.value = _movieDetailState.value.copy(
            movie = resource
        )
    }

    private fun updateMovieReviewsState(resource: Flow<PagingData<Review>>) {
        _movieDetailState.value = _movieDetailState.value.copy(
            reviews = resource
        )
    }
}