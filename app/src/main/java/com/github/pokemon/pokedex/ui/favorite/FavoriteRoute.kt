package com.github.pokemon.pokedex.ui.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.pokemon.pokedex.R
import com.github.pokemon.pokedex.ui.components.SearchListCard
import com.github.pokemon.pokedex.ui.components.common.EmptyScreen
import com.github.pokemon.pokedex.ui.components.common.ErrorScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteRoute(
    onDetail: (String) -> Unit,
    viewModel: FavoriteViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.onAction(FavoriteAction.OnStart)
    }

    FavoriteScreen(
        uiState = uiState,
        onDetailClick = onDetail,
    )
}

@Composable
fun FavoriteScreen(
    uiState: FavoriteUiState,
    onDetailClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        uiState.errorMessage?.let { message ->
            ErrorScreen(
                title = message,
                message = null,
            )
        }

        if (uiState.favorites.isNotEmpty()) {
            LazyColumn(
                modifier = modifier,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {

                item(key = "header") {
                    Text(
                        text = stringResource(id = R.string.favorite_title),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                        ),
                        modifier = Modifier.padding(
                            top = 36.dp,
                            bottom = 16.dp,
                            start = 16.dp,
                            end = 16.dp,
                        ),
                    )
                }

                items(
                    count = uiState.favorites.count(),
                    key = { index -> uiState.favorites[index].id },
                ) { index ->
                    val pokemon = uiState.favorites[index]
                    SearchListCard(
                        number = pokemon.id,
                        name = pokemon.name,
                        types = pokemon.types,
                        imageUrl = pokemon.imageUrl,
                        fallbackThumbUrl = pokemon.imageUrl,
                        isLoadingDetail = false,
                        errorDetail = null,
                        onClick = { onDetailClick(pokemon.id.toString()) },
                    )
                }
            }
        } else if (!uiState.isLoading && uiState.errorMessage == null) {
            EmptyScreen(
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
