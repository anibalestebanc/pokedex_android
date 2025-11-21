package com.github.zsoltk.pokedex.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.zsoltk.pokedex.ui.components.appbar.MainAppBar
import com.github.zsoltk.pokedex.ui.news.NewsSection


@Composable
fun HomeScreen(onMenuItemSelected: (MenuItem) -> Unit) {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        MainAppBar(onMenuItemSelected)
        Column(modifier = Modifier.padding(32.dp)) {
            NewsSection()
        }
    }
}


@Preview
@Composable
fun PreviewMainScreen() {
    HomeScreen(onMenuItemSelected = {})
}

