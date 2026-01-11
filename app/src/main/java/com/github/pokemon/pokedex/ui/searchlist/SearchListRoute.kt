package com.github.pokemon.pokedex.ui.searchlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.pokemon.pokedex.R
import com.github.pokemon.pokedex.domain.model.PokemonCatalog
import com.github.pokemon.pokedex.ui.components.SearchListCard
import com.github.pokemon.pokedex.ui.components.appbar.FakeRoundedSearchInput
import com.github.pokemon.pokedex.ui.components.common.EmptyScreen
import com.github.pokemon.pokedex.ui.components.common.RetryErrorScreen
import com.github.pokemon.pokedex.ui.components.common.LoadingScreen
import com.github.pokemon.pokedex.ui.components.common.LoadingMore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchListRoute(
    initialQuery: String,
    onSearch: (String) -> Unit,
    onDetail: (String) -> Unit,
    viewModel: SearchListViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val pagingItems = viewModel.pagingFlow.collectAsLazyPagingItems()
    var seeded by rememberSaveable { mutableStateOf(false) }


    LaunchedEffect(initialQuery, seeded) {
        if (!seeded) {
            viewModel.onAction(SearchListAction.SetInitialQuery(initialQuery))
            seeded = true
        }
    }

    SearchListScreen(
        uiState = uiState,
        onSearchClick = onSearch,
        onDetailClick = onDetail,
        pagingItems = pagingItems,
        observeDetail = viewModel::observeDetail,
    )
}

@Composable
fun SearchListScreen(
    uiState: SearchListUiState,
    onSearchClick: (String) -> Unit,
    onDetailClick: (String) -> Unit,
    pagingItems: LazyPagingItems<PokemonCatalog>? = null,
    observeDetail: (Int) -> Flow<DetailItemUiState>,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxSize()) {

        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            FakeRoundedSearchInput(
                text = uiState.query.ifBlank { stringResource(id = R.string.search_bar_hint) },
                onSearchClick = { onSearchClick(uiState.query) },
            )
        }

        if (pagingItems == null) return@Column

        val loadState = pagingItems.loadState
        val hasItems = pagingItems.itemCount > 0

        if (!hasItems) {
            when (loadState.refresh) {
                is LoadState.Loading -> {
                    LoadingScreen(message = stringResource(id = R.string.loading))
                    return@Column
                }

                is LoadState.Error -> {
                    RetryErrorScreen(
                        title = stringResource(R.string.error_generic_message),
                        retryText = stringResource(R.string.retry),
                        onRetry = { pagingItems.retry() },
                    )
                }

                is LoadState.NotLoading -> {
                    EmptyScreen(title = stringResource(id = R.string.search_result_empty, uiState.query))
                }
            }
        }

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
                        initialValue = DetailItemUiState(isLoading = true),
                    )
                    SearchListCard(
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

            when (loadState.append) {
                is LoadState.Loading -> item(key = "append-loading") { LoadingMore() }
                is LoadState.Error -> item(key = "append-error") {
                    RetryErrorScreen(
                        title = stringResource(id = R.string.search_result_load_more_error),
                        retryText = stringResource(id = R.string.retry),
                        onRetry = { pagingItems.retry() },
                    )
                }
                else -> Unit
            }
        }
    }
}

@Preview
@Composable
fun SearchListScreenPreview() {
    SearchListScreen(
        onDetailClick = {},
        onSearchClick = {},
        uiState = SearchListUiState(),
        pagingItems = null,
        observeDetail = { flowOf(DetailItemUiState()) }
    )
}

