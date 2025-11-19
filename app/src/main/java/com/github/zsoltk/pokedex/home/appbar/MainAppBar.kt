package com.github.zsoltk.pokedex.home.appbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.zsoltk.pokedex.common.PokeBallBackground
import com.github.zsoltk.pokedex.common.Title
import com.github.zsoltk.pokedex.home.Home
import com.github.zsoltk.pokedex.home.appbar.elements.LargeAppBar
import com.github.zsoltk.pokedex.home.appbar.elements.Menu
import com.github.zsoltk.pokedex.home.appbar.elements.RoundedSearchBar

@Composable
fun MainAppBar(onMenuItemSelected: (Home.MenuItem) -> Unit) {
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
                text = "What Pokémon\nare you looking for?",
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(
                    top = 64.dp,
                    bottom = 24.dp
                )
            )
            RoundedSearchBar()
            Spacer(modifier = Modifier.height(32.dp))
            Menu(onMenuItemSelected)
        }
    }
}

@Preview
@Composable
fun PreviewMainAppBar() {
    MainAppBar(onMenuItemSelected = {})
}
