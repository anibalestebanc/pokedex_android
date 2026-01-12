package com.github.pokemon.pokedex.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pokemon.pokedex.utils.LoggerError
import com.github.pokemon.pokedex.domain.repository.HistorySearchRepository
import com.github.pokemon.pokedex.domain.usecase.SaveHistorySearchUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(
    private val saveHistorySearchUseCase: SaveHistorySearchUseCase,
    private val repository: HistorySearchRepository,
    private val loggerError: LoggerError,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    fun onAction(event: SearchAction) {
        when (event) {
            SearchAction.OnStart -> getSearchHistory()
            is SearchAction.SearchSubmit -> saveSearchQuery(event.query)
            is SearchAction.SelectSuggestion -> saveSearchQuery(event.query)
            SearchAction.RemoveAllHistory -> deleteSearchHistory()
        }
    }

    private fun getSearchHistory() = viewModelScope.launch {
        repository.observeHistorySearch()
            .distinctUntilChanged()
            .catch { error ->
                loggerError("Error getting search history", error)
                _uiState.update { it.copy(historySearch = emptyList()) }
            }
            .collect { history ->
                _uiState.update { it.copy(historySearch = history) }
            }
    }

    private fun saveSearchQuery(query: String) = viewModelScope.launch {
        saveHistorySearchUseCase(query)
            .onFailure {
                loggerError("Error saving search query", it)
            }
    }

    private fun deleteSearchHistory() = viewModelScope.launch {
        repository.clearAll().onFailure {
            loggerError("Error deleting search history", it)
        }
    }
}
