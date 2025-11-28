package com.github.pokemon.pokedex.ui.components.appbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.pokemon.pokedex.R
import com.github.pokemon.pokedex.ui.components.PokeBallBackground
import com.github.pokemon.pokedex.ui.components.Title
import com.github.pokemon.pokedex.ui.home.HomeEvent
import com.github.pokemon.pokedex.ui.home.model.MenuItem

@Composable
fun MainAppBar(
    onSearchBarClicked: () -> Unit,
    onEvent: (HomeEvent) -> Unit,
) {
    LargeAppBar(background = { PokeBallBackground() }) {
        Column(
            modifier = Modifier.padding(
                top = 32.dp,
                start = 32.dp,
                end = 32.dp,
                bottom = 16.dp
            )
        ) {
            Title(
                text = stringResource(id = R.string.main_app_bar_title),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(
                    top = 48.dp,
                    bottom = 24.dp
                ),
            )
            RoundedSearchBar(
                text = stringResource(id = R.string.search_bar_hint),
                onSearchClick = onSearchBarClicked,
            )
            Spacer(modifier = Modifier.height(32.dp))
            Menu(onMenuItemSelected = { menuItem ->
                when (menuItem) {
                    is MenuItem.Pokedex -> onEvent(HomeEvent.OpenPokedex)
                    else -> Unit
                }
            })
        }
    }
}

@Preview
@Composable
fun PreviewMainAppBar() {
    MainAppBar(onEvent = { }, onSearchBarClicked = { })
}
