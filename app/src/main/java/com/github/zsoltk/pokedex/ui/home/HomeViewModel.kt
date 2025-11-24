package com.github.zsoltk.pokedex.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.zsoltk.pokedex.domain.usecase.GetPokemonCatalogUseCase
import com.github.zsoltk.pokedex.navigation.Route
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getPokemonCatalogUseCase: GetPokemonCatalogUseCase
) : ViewModel() {
    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect: SharedFlow<HomeEffect> = _effect

    fun onEvent(action: HomeEvent) = viewModelScope.launch {
        when (action) {
            is HomeEvent.OnStart -> getPokemonCatalog()
            is HomeEvent.OpenPokedex -> _effect.emit(HomeEffect.NavigateTo(Route.PokemonList))
        }
    }

    private fun getPokemonCatalog() = viewModelScope.launch {
        getPokemonCatalogUseCase()
            .onSuccess {
                //Show Home Screen
                Log.d("HomeViewModel", "Success Get Catalog with ${it} items")
            }.onFailure {
                // Todo check if fail catalog
                Log.d("HomeViewModel", "Error to Get Catalog: ${it.message}")
            }
    }
}

