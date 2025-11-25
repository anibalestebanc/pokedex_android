package com.github.zsoltk.pokedex.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.zsoltk.pokedex.domain.model.PokemonCatalog
import com.github.zsoltk.pokedex.ui.components.CustomSearchBar
import com.github.zsoltk.pokedex.ui.components.PokemonSearchCard
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
    LaunchedEffect(Unit) {
        viewModel.onEvent(SearchEvent.OnStart)
    }
    LaunchedEffect(initialQuery) {
        viewModel.onEvent(SearchEvent.SetInitialQuery(initialQuery))
    }

    SearchScreen(
        searchState = state,
        onEvent = viewModel::onEvent,
        items = items,
        viewModel = viewModel,
    )
}

@Composable
fun SearchScreen(
    searchState: SearchUiState,
    onEvent: (SearchEvent) -> Unit,
    items: LazyPagingItems<PokemonCatalog>,
    viewModel: SearchViewModel,
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
            searchState = searchState,
            onEvent = onEvent,
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

                    val detailFlow = remember(p.id) { viewModel.observeDetail(p.id) }
                    val detailUiState by detailFlow.collectAsStateWithLifecycle()

                    PokemonSearchCard(
                        index = index + 1,
                        number = detailUiState.detail?.id,
                        name = p.displayName,
                        types = detailUiState.detail?.types ?: emptyList(),
                        imageUrl = detailUiState.detail?.imageUrl,
                        fallbackThumbUrl = p.url,
                        isLoadingDetail = detailUiState.isLoading,
                        errorDetail = detailUiState.error,
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
