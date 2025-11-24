package com.github.zsoltk.pokedex.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.zsoltk.pokedex.ui.components.appbar.MainAppBar
import com.github.zsoltk.pokedex.ui.news.NewsSection
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeRoute(
    onBackClick: () -> Unit,
    onPokemonListClick: () -> Unit,
    onPokemonSearchClick: (String) -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(HomeEvent.OnStart)
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.NavigateToPokemonList -> onPokemonListClick()
                is HomeEffect.NavigateToSearch -> onPokemonSearchClick(effect.query)
            }
        }
    }
    HomeScreen(state, viewModel::onEvent)
}

@Composable
fun HomeScreen(state: HomeUiState, onEvent: (HomeEvent) -> Unit) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        MainAppBar(
            state = state,
            onEvent = onEvent,
        )
        Column(modifier = Modifier.padding(32.dp)) {
            NewsSection()
        }
    }
}



@Preview
@Composable
fun PreviewMainScreen() {
    HomeScreen(HomeUiState(), {})
}

