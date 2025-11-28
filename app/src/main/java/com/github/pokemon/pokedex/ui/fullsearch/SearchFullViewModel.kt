package com.github.pokemon.pokedex.ui.fullsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pokemon.pokedex.domain.repository.HistorySearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchFullViewModel(private val repository: HistorySearchRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchFullUiState())
    val uiState: StateFlow<SearchFullUiState> = _uiState

    fun onEvent(event: SearchFullEvent) = when (event) {
        SearchFullEvent.OnStart -> getSearchHistory()
        is SearchFullEvent.SetInitialQuery -> initialQuery(event.query)
        is SearchFullEvent.SearchSubmit -> submitSearch()
        is SearchFullEvent.SelectSuggestion ->  selectSearchSuggestion(event.query)
        is SearchFullEvent.QueryChanged -> onQueryChanged(event.query)
        SearchFullEvent.RemoveAllHistory -> deleteSearchHistory()
    }

    private fun initialQuery(query: String) {
        _uiState.update { it.copy(query = query) }
    }

    private fun getSearchHistory() = viewModelScope.launch {
        repository.getHistorySearch(10)
            .collect { history ->
                _uiState.update { it.copy(searchHistory = history) }
            }
    }

    private fun saveSearchQuery(query: String) = viewModelScope.launch {
        if (query.isNotBlank()) {
            repository.save(query)
        }
    }

    private fun onQueryChanged(query: String) {
        _uiState.update { it.copy(query = query) }
    }

    private fun selectSearchSuggestion(query: String) {
        _uiState.update { it.copy(query = query) }
        saveSearchQuery(query)
    }

    private fun submitSearch() {
        val query = _uiState.value.query
        saveSearchQuery(query)
    }

    private fun deleteSearchHistory() = viewModelScope.launch {
        repository.clearAll()
    }
}
