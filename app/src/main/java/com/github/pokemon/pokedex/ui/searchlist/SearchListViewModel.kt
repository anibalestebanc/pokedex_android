package com.github.pokemon.pokedex.ui.searchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.pokemon.pokedex.domain.model.PokemonCatalog
import com.github.pokemon.pokedex.domain.usecase.SearchListUseCases
import com.github.pokemon.pokedex.utils.ErrorMapper
import com.github.pokemon.pokedex.utils.LoggerError
import com.github.pokemon.pokedex.utils.emptyString
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

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchListViewModel(
    private val searchListUseCases: SearchListUseCases,
    private val loggerError: LoggerError,
    private val errorMapper: ErrorMapper
) : ViewModel() {

    private val queryFlow = MutableStateFlow(emptyString)
    private val detailFlows = mutableMapOf<Int, StateFlow<DetailItemUiState>>()

    @Suppress("MagicNumber")
    val pagingFlow: Flow<PagingData<PokemonCatalog>> =
        queryFlow
            .debounce(1_000)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                searchListUseCases.searchPokemonPagedUseCase(query)
            }.cachedIn(viewModelScope)

    @Suppress("MagicNumber")
    fun observeDetail(id: Int): StateFlow<DetailItemUiState> = detailFlows.getOrPut(id) {
        searchListUseCases.observeAndRefreshDetailUseCase(id)
            .map { detail ->
                DetailItemUiState(detail = detail, isLoading = false, error = null)
            }.onStart {
                emit(DetailItemUiState(isLoading = true))
            }.catch { error ->
                loggerError("Error to get pokemon detail with id $id", error)
                emit(DetailItemUiState(isLoading = false, error = errorMapper(error)))
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                initialValue = DetailItemUiState(isLoading = true),
            )
    }

    fun onAction(event: SearchListAction) = when (event) {
        is SearchListAction.SubmitSearch -> submitSearch(event.query)
    }

    private fun submitSearch(query: String) {
        val currentQuery = queryFlow.value
        if (currentQuery != query) {
            queryFlow.value = query
        }
    }
}
