package com.github.pokemon.pokedex.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pokemon.pokedex.R
import com.github.pokemon.pokedex.core.common.error.NotFoundException
import com.github.pokemon.pokedex.domain.usecase.GetPokemonFullDetailUseCase
import com.github.pokemon.pokedex.domain.usecase.ObserveIsFavoriteUseCase
import com.github.pokemon.pokedex.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getFullDetailUseCase: GetPokemonFullDetailUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val observeIsFavoriteUseCase: ObserveIsFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState

    private val _uiEffect = MutableSharedFlow<DetailUiEffect>()
    val uiEffect: SharedFlow<DetailUiEffect> = _uiEffect


    fun observeIsFavorite(id: String): Flow<Boolean> = observeIsFavoriteUseCase(id.toInt())

    fun onAction(action: DetailAction) = viewModelScope.launch {
        when (action) {
            is DetailAction.OnStart -> getPokemonDetail(action.pokemonId)
            is DetailAction.OnRetryDetailClick -> retryDetail(action.pokemonId)
            is DetailAction.OnToggleFavorite -> onToggleFavorite(action.pokemonId)
            is DetailAction.OnSharePokemon -> _uiEffect.emit(DetailUiEffect.ShareUrl(action.imageUrl))
        }
    }
    private fun getPokemonDetail(idOrName: String) = viewModelScope.launch {
        val id = idOrName.toIntOrNull()
        if (id == null) {
            _uiState.value = DetailUiState(error = R.string.error_generic_message)
            return@launch
        }
        _uiState.value = DetailUiState(isLoading = true)
        getFullDetailUseCase(idOrName.toInt())
            .onSuccess { detail ->
                _uiState.value = DetailUiState(data = detail)
            }.onFailure { e ->
                if (e is NotFoundException) {
                    _uiState.value = DetailUiState(error = R.string.error_not_found_message)
                    return@launch
                }
                _uiState.value = DetailUiState(error = R.string.error_generic_message)
            }
    }

    private fun onToggleFavorite(pokemonId: String) = viewModelScope.launch {
        pokemonId.toIntOrNull()?.let { id ->
            toggleFavoriteUseCase(id)
        }
    }

    private fun retryDetail(idOrName: String) = getPokemonDetail(idOrName)

}
