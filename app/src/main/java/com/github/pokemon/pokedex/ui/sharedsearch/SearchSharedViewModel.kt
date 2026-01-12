package com.github.pokemon.pokedex.ui.sharedsearch

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.github.pokemon.pokedex.utils.emptyString
import kotlinx.coroutines.flow.StateFlow

private const val QUERY_KEY = "query"

class SearchSharedViewModel(val savedStateHandle: SavedStateHandle) : ViewModel() {
    val sharedQuery: StateFlow<String> =
        savedStateHandle.getStateFlow(QUERY_KEY, emptyString)


    fun onAction(action: SharedSearchAction) {
        when (action) {
            is SharedSearchAction.QueryChanged -> {
                savedStateHandle[QUERY_KEY] = action.query
            }

            SharedSearchAction.ClearQuery -> {
                savedStateHandle[QUERY_KEY] = emptyString
            }
        }
    }
}
