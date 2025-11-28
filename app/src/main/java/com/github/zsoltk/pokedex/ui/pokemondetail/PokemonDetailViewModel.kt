package com.github.zsoltk.pokedex.ui.pokemondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.zsoltk.pokedex.R
import com.github.zsoltk.pokedex.domain.usecase.GetPokemonFullDetailUseCase
import com.github.zsoltk.pokedex.domain.usecase.ObserveIsFavoriteUseCase
import com.github.zsoltk.pokedex.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val getFullDetailUseCase: GetPokemonFullDetailUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val observeIsFavoriteUseCase: ObserveIsFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState

    fun observeIsFavorite(id: String): Flow<Boolean> = observeIsFavoriteUseCase(id.toInt())

    fun onEvent(action: DetailEvent) = viewModelScope.launch {
        when (action) {
            is DetailEvent.OnStart -> getPokemonDetail(action.pokemonId)
            is DetailEvent.OnRetryClick -> retry(action.pokemonId)
            is DetailEvent.OnToggleFavorite -> onToggleFavorite(action.pokemonId)
        }
    }
    fun getPokemonDetail(idOrName: String) = viewModelScope.launch {
        _uiState.value = DetailUiState(isLoading = true)
        getFullDetailUseCase(idOrName).onSuccess { detail ->
            _uiState.value = DetailUiState(data = detail)
        }.onFailure { e ->
            _uiState.value = DetailUiState(error = R.string.error_generic_message)
        }
    }

    fun onToggleFavorite(pokemonId: String) = viewModelScope.launch {
        val id = pokemonId.toIntOrNull()
        if (id != null) {
            toggleFavoriteUseCase(id)
        }
    }

    fun retry(idOrName: String) = getPokemonDetail(idOrName)

}
