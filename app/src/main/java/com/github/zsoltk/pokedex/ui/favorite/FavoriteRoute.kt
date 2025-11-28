package com.github.zsoltk.pokedex.ui.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.zsoltk.pokedex.R
import com.github.zsoltk.pokedex.ui.components.PokemonSearchCard
import com.github.zsoltk.pokedex.ui.components.common.EmptyStateScreen
import com.github.zsoltk.pokedex.ui.components.common.ErrorStateScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteRoute(
    onBackClick: () -> Unit,
    onDetailClick: (String) -> Unit,
    viewModel: FavoriteViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(FavoriteEvent.OnStart)
    }

    FavoriteScreen(
        uiState = uiState,
        onDetailClick = onDetailClick,
    )
}

@Composable
fun FavoriteScreen(
    uiState: FavoriteUiState,
    onDetailClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(Modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        uiState.error?.let {
            ErrorStateScreen(
                title = stringResource(id = it),
                message = null,
            )
        }

        if (uiState.favorites.isNotEmpty()) {
            LazyColumn(
                modifier = modifier,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    count = uiState.favorites.count(),
                    key = { index -> uiState.favorites[index].id }
                ) { index ->
                    val pokemon = uiState.favorites[index]
                    PokemonSearchCard(
                        index = index + 1,
                        number = pokemon.id,
                        name = pokemon.name,
                        types = pokemon.types,
                        imageUrl = pokemon.imageUrl,
                        fallbackThumbUrl = pokemon.imageUrl,
                        isLoadingDetail = false,
                        errorDetail = null,
                        onClick = { onDetailClick(pokemon.id.toString()) }
                    )
                }
            }
        } else if (!uiState.isLoading && uiState.error == null) {
            EmptyStateScreen(
                title = stringResource(id = R.string.favorite_no_favorites_yet),
                message = null,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteScreenPreview() {
    FavoriteScreen(
        uiState = FavoriteUiState(),
        onDetailClick = {},
    )
}
