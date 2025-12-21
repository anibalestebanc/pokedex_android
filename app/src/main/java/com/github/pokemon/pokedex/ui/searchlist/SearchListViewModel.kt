package com.github.pokemon.pokedex.ui.searchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.pokemon.pokedex.domain.model.PokemonCatalog
import com.github.pokemon.pokedex.domain.repository.PokemonDetailRepository
import com.github.pokemon.pokedex.domain.usecase.SearchPokemonUseCase
import com.github.pokemon.pokedex.utils.ErrorMapper
import com.github.pokemon.pokedex.utils.LoggerError
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchListViewModel(
    private val searchPokemonUseCase: SearchPokemonUseCase,
    private val pokemonDetailRepository: PokemonDetailRepository,
    private val loggerError: LoggerError,
    private val errorMapper: ErrorMapper
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchListUiState())
    val uiState: StateFlow<SearchListUiState> = _uiState

    private val queryFlow = MutableStateFlow("")
    private val detailFlows = mutableMapOf<Int, StateFlow<DetailItemUiState>>()

    val pagingFlow: Flow<PagingData<PokemonCatalog>> =
        queryFlow
            .debounce(1_000)
            .distinctUntilChanged()
            .flatMapLatest { q ->
                searchPokemonUseCase(q.ifBlank { null })
            }.cachedIn(viewModelScope)

    fun observeDetail(id: Int): StateFlow<DetailItemUiState> = detailFlows.getOrPut(id) {
        pokemonDetailRepository.observePokemonDetail(id)
            .map { detail ->
                DetailItemUiState(detail = detail, isLoading = false, error = null)
            }.onStart {
                emit(DetailItemUiState(isLoading = true))
            }.catch { error ->
                loggerError.logError("Error to get pokemon detail with id $id", Exception(error))
                emit(DetailItemUiState(isLoading = false, error = errorMapper.toMessage(error)))
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                initialValue = DetailItemUiState(isLoading = true),
            )
    }

    fun onAction(event: SearchListAction) = when (event) {
        is SearchListAction.SetInitialQuery -> initialSearch(event.text)
        is SearchListAction.QueryChanged -> onQueryChanged(event.text)
        SearchListAction.SubmitSearch -> submitSearch()
    }

    private fun onQueryChanged(query: String) {
        _uiState.update { it.copy(query = query) }
    }

    private fun initialSearch(query: String) {
        _uiState.update { it.copy(query = query) }
        queryFlow.value = query
    }

    private fun submitSearch() {
        val query = _uiState.value.query
        queryFlow.value = query
    }
}
