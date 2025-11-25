package com.github.zsoltk.pokedex.ui.pokemondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.zsoltk.pokedex.domain.usecase.GetPokemonFullDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val getFullDetailUseCase: GetPokemonFullDetailUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState

    fun onEvent(action: DetailEvent) = viewModelScope.launch {
        when (action) {
            is DetailEvent.OnStart -> getPokemonDetail(action.idOrName)
            is DetailEvent.OnRetryClick -> retry(action.idOrName)
        }
    }

    fun getPokemonDetail(idOrName: String) = viewModelScope.launch {
        _uiState.value = DetailUiState(isLoading = true)
        getFullDetailUseCase(idOrName).onSuccess { detail ->
            _uiState.value = DetailUiState(data = detail)
        }.onFailure { e ->
            _uiState.value = DetailUiState(error = e.message ?: "Error")
        }
    }

    fun retry(idOrName: String) = getPokemonDetail(idOrName)

}
