package com.github.pokemon.pokedex.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class HomeViewModel() : ViewModel() {
    private val _uiEffect = MutableSharedFlow<HomeEffect>()
    val uiEffect: SharedFlow<HomeEffect> = _uiEffect

    fun onAction(action: HomeAction) = viewModelScope.launch {
        when (action) {
            HomeAction.OpenPokedex -> _uiEffect.emit(HomeEffect.NavigateToPokedex)
            HomeAction.OnSearchClick -> _uiEffect.emit(HomeEffect.NavigateToSearch)
        }
    }
}

