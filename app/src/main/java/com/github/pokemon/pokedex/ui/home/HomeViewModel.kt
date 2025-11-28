package com.github.pokemon.pokedex.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pokemon.pokedex.ui.home.HomeEffect.NavigateToPokemonList
import com.github.pokemon.pokedex.ui.home.HomeEffect.NavigateToSearch
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState
    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect: SharedFlow<HomeEffect> = _effect

    fun onEvent(action: HomeEvent) = viewModelScope.launch {
        when (action) {
            is HomeEvent.OnStart -> Unit
            is HomeEvent.OpenPokedex -> _effect.emit(NavigateToPokemonList)
            is HomeEvent.SearchQueryChanged -> updateQueryChanged(action.text)
            HomeEvent.SearchSubmitted -> submitSearch()
        }
    }

    private fun updateQueryChanged(text: String) {
        _uiState.update { it.copy(query = text) }
    }

    private fun submitSearch() = viewModelScope.launch {
        val q = _uiState.value.query.trim()
        if (q.isNotEmpty()) {
            _effect.emit(NavigateToSearch(q))
        }
    }
}

