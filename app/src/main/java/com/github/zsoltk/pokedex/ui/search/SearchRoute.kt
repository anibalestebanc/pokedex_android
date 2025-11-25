package com.github.zsoltk.pokedex.ui.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.zsoltk.pokedex.domain.model.PokemonCatalog
import com.github.zsoltk.pokedex.ui.components.CustomSearchBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchRoute(
    initialQuery: String,
    onBackClick: () -> Unit,
    onDetailClick: (String) -> Unit,
    viewModel: SearchViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val items = viewModel.pagingFlow.collectAsLazyPagingItems()

    LaunchedEffect(initialQuery) {
        viewModel.onEvent(SearchEvent.SetInitialQuery(initialQuery))
    }

    SearchScreen(state, viewModel::onEvent, items)
}

@Composable
fun SearchScreen(
    searchState: SearchUiState,
    onEvent: (SearchEvent) -> Unit,
    items: LazyPagingItems<PokemonCatalog>,
    modifier: Modifier = Modifier,
) {
    Column(Modifier.fillMaxSize()) {
        CustomSearchBar(
            query = searchState.query,
            onQueryChange = { newQuery ->
                onEvent(SearchEvent.QueryChanged(newQuery))
            },
            onSearch = {
                onEvent(SearchEvent.SearchSubmit)
            },
        )

        val loadState = items.loadState
        val isEmptyList = items.itemCount == 0 &&
            loadState.refresh is LoadState.NotLoading &&
            searchState.query.isNotBlank()

        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                count = items.itemCount,
                key = { index ->
                    val item = items[index]
                    item?.id ?: "placeholder-$index"
                },
            ) { index ->
                items[index]?.let { p ->
                    PokemonRow(
                        index = index,
                        name = p.name,
                        artworkUrl = p.url,
                        onClick = { },
                    )
                }
            }
            when (val ls = items.loadState.refresh) {
                is LoadState.Loading -> {
                    item(key = "refresh-loading") { LoadingRow() }
                }

                is LoadState.Error -> {
                    item(key = "refresh-error") { ErrorRow(ls.error.message ?: "Error") { items.retry() } }
                }

                else -> Unit
            }
            if (items.loadState.append is LoadState.Loading) {
                item(key = "append-loading") { LoadingRow() }
            }
            if (loadState.append is LoadState.Error) {
                val err = loadState.append as LoadState.Error
                item(key = "append-error") {
                    ErrorRow(
                        message = err.error.message ?: "Error al cargar más",
                        onRetry = { items.retry() },
                    )
                }
            }

            if (isEmptyList) {
                item(key = "empty") {
                    EmptyStateRow(
                        text = "No se encontraron resultados para ${searchState.query}",
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonRow(
    index: Int,
    name: String,
    artworkUrl: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        OutlinedCard(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 100.dp),
        ) {
            Text(
                text = "$index - $name",
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun LoadingRow() {
    Box(modifier = Modifier.fillMaxWidth()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
fun ErrorRow(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = message)
    }
}

@Composable
fun EmptyStateRow(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text)
    }
}
