package com.github.zsoltk.pokedex.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.zsoltk.pokedex.R
import com.github.zsoltk.pokedex.home.appbar.MainAppBar
import com.github.zsoltk.pokedex.home.news.NewsSection

interface Home {

    sealed class MenuItem(
        val label: String,
        val colorResId: Int
    ) {
        object Pokedex : MenuItem("Pokedex", R.color.poke_teal)
        object Moves : MenuItem("Moves", R.color.poke_red)
        object Abilities : MenuItem("Abilities", R.color.poke_light_blue)
        object Items : MenuItem("Items", R.color.poke_yellow)
        object Locations : MenuItem("Locations", R.color.poke_purple)
        object TypeCharts : MenuItem("Type charts", R.color.poke_brown)
    }

    companion object {
        @Composable
        fun Content(onMenuItemSelected: (MenuItem) -> Unit) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                MainAppBar(onMenuItemSelected)
                Column(modifier = Modifier.padding(32.dp)) {
                    NewsSection()
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewMainScreen() {
    Home.Content(onMenuItemSelected = {})
}
