package com.github.zsoltk.pokedex.ui.searchresult

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.zsoltk.pokedex.domain.model.PokemonCatalog
import com.github.zsoltk.pokedex.navigation.AppState
import com.github.zsoltk.pokedex.ui.components.PokemonSearchCard
import com.github.zsoltk.pokedex.ui.components.appbar.RoundedSearchBar
import com.github.zsoltk.pokedex.ui.searchresult.navigation.SEARCH_RESULT_KEY
import com.github.zsoltk.pokedex.utils.getAndConsume
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchResultRoute(
    appState: AppState,
    initialQuery: String,
    onSearchClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onDetailClick: (String) -> Unit,
    viewModel: SearchResultViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pagingItems = viewModel.pagingFlow.collectAsLazyPagingItems()
    var seeded by rememberSaveable { mutableStateOf(false) }

    val backStackEntry by appState.navController.currentBackStackEntryAsState()
    LaunchedEffect(backStackEntry) {
        backStackEntry?.savedStateHandle?.getAndConsume<String>(SEARCH_RESULT_KEY)?.let { q ->
            viewModel.onEvent(SearchResultEvent.QueryChanged(q))
            viewModel.onEvent(SearchResultEvent.SubmitSearch)
        }
    }

    LaunchedEffect(initialQuery, seeded) {
        if (!seeded) {
            viewModel.onEvent(SearchResultEvent.SetInitialQuery(initialQuery))
            seeded = true
        }
    }

    SearchScreen(
        uiState = uiState,
        onSearchClick = onSearchClick,
        onDetailClick = onDetailClick,
        pagingItems = pagingItems,
        observeDetail = viewModel::observeDetail,
    )
}

@Composable
fun SearchScreen(
    uiState: SearchResultUiState,
    onSearchClick: (String) -> Unit,
    onDetailClick: (String) -> Unit,
    pagingItems: LazyPagingItems<PokemonCatalog>? = null,
    observeDetail: (Int) -> Flow<PokemonDetailUiState>,
    modifier: Modifier = Modifier,
) {
    Column(Modifier.fillMaxSize()) {

        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            RoundedSearchBar(
                text = uiState.query.ifBlank { "Search Pokemon" },
                onSearchClick = { onSearchClick(uiState.query) },
            )
        }

        val listContent: @Composable () -> Unit = {
            if (pagingItems != null) {
                val loadState = pagingItems.loadState
                val isEmptyList = pagingItems.itemCount == 0 &&
                    loadState.refresh is LoadState.NotLoading &&
                    uiState.query.isNotBlank()

                LazyColumn(
                    modifier = modifier,
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(
                        count = pagingItems.itemCount,
                        key = { index -> pagingItems[index]?.id ?: "placeholder-$index" }
                    ) { index ->
                        pagingItems[index]?.let { p ->

                            val detailUiState by remember(p.id) { observeDetail(p.id) }.collectAsStateWithLifecycle(
                                initialValue = PokemonDetailUiState(isLoading = true),
                            )

                            PokemonSearchCard(
                                index = index + 1,
                                number = detailUiState.detail?.id,
                                name = p.displayName,
                                types = detailUiState.detail?.types ?: emptyList(),
                                imageUrl = detailUiState.detail?.imageUrl,
                                fallbackThumbUrl = p.url,
                                isLoadingDetail = detailUiState.isLoading,
                                errorDetail = detailUiState.error,
                                onClick = { onDetailClick(p.id.toString()) },
                            )
                        }
                    }
                    when (val ls = pagingItems.loadState.refresh) {
                        is LoadState.Loading -> item(key = "refresh-loading") { LoadingRow() }
                        is LoadState.Error -> item(key = "refresh-error") {
                            ErrorRow(ls.error.message ?: "Error") { pagingItems.retry() }
                        }
                        else -> Unit
                    }
                    if (pagingItems.loadState.append is LoadState.Loading) {
                        item(key = "append-loading") { LoadingRow() }
                    }
                    if (loadState.append is LoadState.Error) {
                        val err = loadState.append as LoadState.Error
                        item(key = "append-error") {
                            ErrorRow(err.error.message ?: "Error al cargar más") { pagingItems.retry() }
                        }
                    }

                    if (isEmptyList) {
                        item(key = "empty") { EmptyStateRow("No se encontraron resultados para ${uiState.query}") }
                    }
                }

            }
        }
        listContent()
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

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(
        onDetailClick = {},
        onSearchClick = {},
        uiState = SearchResultUiState(),
        pagingItems = null,
        observeDetail = { flowOf(PokemonDetailUiState()) }
    )
}

