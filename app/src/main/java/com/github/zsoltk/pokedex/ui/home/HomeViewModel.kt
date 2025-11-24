package com.github.zsoltk.pokedex.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.zsoltk.pokedex.navigation.Route
import com.github.zsoltk.pokedex.ui.home.HomeEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect: SharedFlow<HomeEffect> = _effect

    fun onEvent(action: HomeEvent) = viewModelScope.launch {
        when (action) {
            is HomeEvent.OpenPokedex -> _effect.emit(HomeEffect.NavigateTo(Route.PokemonList))
        }
    }
}
