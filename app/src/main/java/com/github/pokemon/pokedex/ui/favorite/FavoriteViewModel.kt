package com.github.pokemon.pokedex.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pokemon.pokedex.domain.usecase.GetFavoritesUseCase
import com.github.pokemon.pokedex.utils.ErrorMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val errorMapper: ErrorMapper,
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoriteUiState())
    val uiState = _uiState.asStateFlow()

    fun onAction(event: FavoriteAction) {
        when (event) {
            is FavoriteAction.OnStart -> getFavorites()
        }
    }

    private fun getFavorites() = viewModelScope.launch {
        getFavoritesUseCase()
            .onStart {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            }
            .catch { error ->
                _uiState.update { it.copy(isLoading = false, errorMessage = errorMapper.toMessage(error)) }
            }
            .collect { favorites ->
                _uiState.update { it.copy(isLoading = false, favorites = favorites) }
            }
    }
}
