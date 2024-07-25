package com.alvaro.movieapp.features.search

import androidx.compose.material3.TextField
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alvaro.movieapp.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    movieUseCase: MovieUseCase
): ViewModel() {
    private val _searchInput = MutableStateFlow(TextFieldValue(""))
    val searchInput
        get() = _searchInput
    
    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val results = _searchInput
        .debounce(300L)
        .distinctUntilChanged()
        .flatMapLatest { 
            movieUseCase.getSearchResult(it.text.trim())
        }
        .cachedIn(viewModelScope)

    fun updateSearchQuery(query: TextFieldValue) {
        _searchInput.value = query
    }
}