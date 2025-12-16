package com.github.pokemon.pokedex.ui.searchresult

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.pokemon.pokedex.domain.model.PokemonCatalog
import com.github.pokemon.pokedex.domain.repository.PokemonDetailRepository
import com.github.pokemon.pokedex.domain.usecase.SearchPokemonUseCase
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
class SearchResultViewModel(
    private val searchPokemonUseCase: SearchPokemonUseCase,
    private val pokemonDetailRepository: PokemonDetailRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchResultUiState())
    val uiState: StateFlow<SearchResultUiState> = _uiState

    private val queryFlow = MutableStateFlow("")
    private val detailFlows = mutableMapOf<Int, StateFlow<PokemonDetailUiState>>()

    val pagingFlow: Flow<PagingData<PokemonCatalog>> =
        queryFlow
            .debounce(1_000)
            .distinctUntilChanged()
            .flatMapLatest { q ->
                searchPokemonUseCase(q.ifBlank { null })
            }.cachedIn(viewModelScope)

    fun observeDetail(id: Int): StateFlow<PokemonDetailUiState> = detailFlows.getOrPut(id) {
        pokemonDetailRepository.observePokemonDetail(id)
            .map { detail ->
                PokemonDetailUiState(detail = detail, isLoading = false, error = null)
            }.onStart {
                emit(PokemonDetailUiState(isLoading = true))
            }.catch { e ->
                emit(PokemonDetailUiState(isLoading = false, error = e.message))
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                initialValue = PokemonDetailUiState(isLoading = true),
            )
    }

    fun onEvent(event: SearchResultEvent) = when (event) {
        is SearchResultEvent.SetInitialQuery -> initialSearch(event.text)
        is SearchResultEvent.QueryChanged -> onQueryChanged(event.text)
        SearchResultEvent.SubmitSearch -> submitSearch()
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
