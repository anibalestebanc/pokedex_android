package com.github.pokemon.pokedex.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pokemon.pokedex.utils.LoggerError
import com.github.pokemon.pokedex.domain.exception.PokeException
import com.github.pokemon.pokedex.domain.repository.HistorySearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: HistorySearchRepository,
    private val loggerError: LoggerError,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    fun onAction(event: SearchAction) {
        when (event) {
            SearchAction.OnStart -> getSearchHistory()
            is SearchAction.SetInitialQuery -> initialQuery(event.query)
            is SearchAction.SearchSubmit -> submitSearch()
            is SearchAction.SelectSuggestion -> selectSearchSuggestion(event.query)
            is SearchAction.QueryChanged -> onQueryChanged(event.query)
            SearchAction.RemoveAllHistory -> deleteSearchHistory()
        }
    }

    private fun initialQuery(query: String) {
        _uiState.update { it.copy(query = query) }
    }

    private fun getSearchHistory() = viewModelScope.launch {
        repository.getHistorySearch(10)
            .catch { error ->
                loggerError.logError(
                    PokeException.UnknownException(
                        message = "Error getting search history",
                        cause = error
                    )
                )
                _uiState.update { it.copy(searchHistory = emptyList()) }
            }
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
