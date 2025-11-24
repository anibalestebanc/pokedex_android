package com.github.zsoltk.pokedex.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.zsoltk.pokedex.ui.components.appbar.MainAppBar
import com.github.zsoltk.pokedex.ui.home.model.MenuItem
import com.github.zsoltk.pokedex.ui.news.NewsSection
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeRoute(
    onBackClick: () -> Unit,
    onPokemonListClick: () -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is HomeEffect.NavigateTo -> onPokemonListClick()
            }
        }
    }
    HomeScreen(viewModel::onEvent)

}

@Composable
fun HomeScreen(onEvent: (HomeEvent) -> Unit) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        MainAppBar(
            // TODO Implement mapper -> MenuItem to HomeOptions
            onMenuItemSelected = { menuItem ->
                when (menuItem) {
                    is MenuItem.Pokedex -> onEvent(HomeEvent.OpenPokedex)
                    else -> Unit
                }
            },
        )
        Column(modifier = Modifier.padding(32.dp)) {
            NewsSection()
        }
    }
}



@Preview
@Composable
fun PreviewMainScreen() {
    HomeScreen(onEvent = {})
}

