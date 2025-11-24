package com.github.zsoltk.pokedex.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.zsoltk.pokedex.domain.usecase.EnqueueDailySyncCatalogUseCase
import com.github.zsoltk.pokedex.navigation.Route
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val enqueueDailySyncCatalogUseCase: EnqueueDailySyncCatalogUseCase,
) : ViewModel() {
    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect: SharedFlow<HomeEffect> = _effect

    fun onEvent(action: HomeEvent) = viewModelScope.launch {
        when (action) {
            is HomeEvent.OnStart -> syncPokemonCatalog()
            is HomeEvent.OpenPokedex -> _effect.emit(HomeEffect.NavigateTo(Route.PokemonList))
        }
    }

    private fun syncPokemonCatalog() = viewModelScope.launch {
        enqueueDailySyncCatalogUseCase()
    }
}

