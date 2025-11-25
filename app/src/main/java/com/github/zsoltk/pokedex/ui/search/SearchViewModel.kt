package com.github.zsoltk.pokedex.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.zsoltk.pokedex.domain.model.PokemonCatalog
import com.github.zsoltk.pokedex.domain.usecase.SearchPokemonUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchViewModel(
    private val searchPokemon: SearchPokemonUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    private val queryFlow = MutableStateFlow("")

    val pagingFlow: Flow<PagingData<PokemonCatalog>> =
        queryFlow
            .debounce(1000)
            .distinctUntilChanged()
            .flatMapLatest { q ->
                searchPokemon(q.ifBlank { null })
            }.cachedIn(viewModelScope)

    fun onEvent(event: SearchEvent) = when (event) {
        is SearchEvent.QueryChanged -> onQueryChanged(event.text)
        SearchEvent.SearchSubmit -> submitSearch()
        is SearchEvent.SetInitialQuery -> initialSearch(event.text)
    }

    private fun onQueryChanged(query: String) {
        _uiState.update { it.copy(query = query) }
    }

    private fun initialSearch(query: String) {
        _uiState.update { it.copy(query = query) }
        queryFlow.value = query
    }

    private fun submitSearch() {
        queryFlow.value = _uiState.value.query
    }
}
