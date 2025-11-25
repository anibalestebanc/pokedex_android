package com.github.zsoltk.pokedex.ui.pokemondetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.zsoltk.pokedex.domain.model.PokemonFullDetail
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonDetailRoute(
    onBackClick: () -> Unit,
    pokemonName: String,
    viewModel: PokemonDetailViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(pokemonName) {
        viewModel.onEvent(DetailEvent.OnStart(pokemonName))
    }

    PokemonDetailScreenV2(pokemonName, state = uiState, onEvent = viewModel::onEvent, onBackClick)
}

@Composable
fun PokemonDetailScreenV2(
    pokemonName: String,
    state: DetailUiState,
    onEvent: (DetailEvent) -> Unit,
    onBackClick: () -> Unit,
) {
    when {
        state.isLoading && state.data == null -> LoadingState()
        state.error != null && state.data == null -> ErrorState(
            message = state.error ?: "Error",
            onRetry = {
                onEvent(DetailEvent.OnRetryClick(pokemonName))
            },
        )

        state.data != null -> PokemonDetailContent(
            data = state.data,
            onRefresh = { onEvent.invoke(DetailEvent.OnRetryClick(pokemonName)) },
            onBack = onBackClick,
        )
    }
}

@Composable
fun ErrorState(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onRetry() },
        contentAlignment = Alignment.Center,
    ) {
        Text(text = message)
    }
}

@Composable
fun LoadingState() {
    Box(modifier = Modifier.fillMaxWidth()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
fun PokemonDetailContent(
    data: PokemonFullDetail,
    onRefresh: () -> Unit,
    onBack: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(text = data.toString())
    }
}

@Preview
@Composable
fun PokemonDetailScreenV2Preview() {
    /* PokeAppTheme {
         PokemonDetailScreenV2("Pikachu")
     } */
}

