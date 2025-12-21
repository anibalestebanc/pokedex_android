package com.github.pokemon.pokedex.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pokemon.pokedex.R
import com.github.pokemon.pokedex.domain.usecase.GetPokemonFullDetailUseCase
import com.github.pokemon.pokedex.domain.usecase.ObserveIsFavoriteUseCase
import com.github.pokemon.pokedex.domain.usecase.ToggleFavoriteUseCase
import com.github.pokemon.pokedex.utils.ErrorMapper
import com.github.pokemon.pokedex.utils.LoggerError
import com.github.pokemon.pokedex.utils.StringProvider
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailViewModel(
    private val getFullDetailUseCase: GetPokemonFullDetailUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    private val observeIsFavoriteUseCase: ObserveIsFavoriteUseCase,
    private val loggerError: LoggerError,
    private val errorMapper: ErrorMapper,
    private val stringProvider: StringProvider,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState

    private val _uiEffect = MutableSharedFlow<DetailUiEffect>(
        replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val uiEffect: SharedFlow<DetailUiEffect> = _uiEffect


    fun observeIsFavorite(id: String): Flow<Boolean> = observeIsFavoriteUseCase(id.toInt())
        .catch { error ->
            loggerError.logError("Error to get observe is favorite with id: $id", Exception(error))
        }

    fun onAction(action: DetailAction) = viewModelScope.launch {
        when (action) {
            is DetailAction.OnStart -> getPokemonDetail(action.pokemonId)
            is DetailAction.OnRetryDetailClick -> retryDetail(action.pokemonId)
            is DetailAction.OnToggleFavorite -> onToggleFavorite(action.pokemonId)
            is DetailAction.OnSharePokemon -> _uiEffect.emit(DetailUiEffect.ShareUrl(action.imageUrl))
        }
    }

    private fun getPokemonDetail(idOrName: String) = viewModelScope.launch {
        //TODO use INT for pokemon ID
        val id = idOrName.toIntOrNull()
        if (id == null) {
            _uiState.value = DetailUiState(errorMessage = stringProvider(R.string.error_generic_message))
            return@launch
        }
        _uiState.value = DetailUiState(isLoading = true)
        getFullDetailUseCase(idOrName.toInt())
            .onSuccess { detail ->
                _uiState.value = DetailUiState(data = detail)
            }.onFailure { error ->
                loggerError.logError("Error to get pokemon detail with id $idOrName", Exception(error))
                _uiState.value = DetailUiState(errorMessage = errorMapper.toMessage(error))
            }
    }

    private fun onToggleFavorite(pokemonId: String) = viewModelScope.launch {
        val id = pokemonId.toIntOrNull() ?: return@launch
        toggleFavoriteUseCase(id)
            .onFailure { error ->
                loggerError.logError("Error to toggle favorite with id $pokemonId", Exception(error))
            }
    }

    private fun retryDetail(idOrName: String) = getPokemonDetail(idOrName)

}
