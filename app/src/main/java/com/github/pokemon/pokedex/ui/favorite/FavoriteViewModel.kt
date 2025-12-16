package com.github.pokemon.pokedex.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pokemon.pokedex.R
import com.github.pokemon.pokedex.domain.usecase.GetFavoritesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteViewModel(private val getFavoritesUseCase: GetFavoritesUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoriteUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(event: FavoriteAction) {
        when (event) {
            is FavoriteAction.OnStart -> getFavorites()
        }
    }

    private fun getFavorites() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true, error = null) }
        getFavoritesUseCase()
            .catch { error ->
                _uiState.update { it.copy(isLoading = false, error = R.string.favorite_error_loading) }
            }
            .collect { favorites ->
                _uiState.update { it.copy(isLoading = false, favorites = favorites) }
            }
    }
}
