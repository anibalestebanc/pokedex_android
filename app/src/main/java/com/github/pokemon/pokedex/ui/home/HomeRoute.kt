package com.github.pokemon.pokedex.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.github.pokemon.pokedex.R
import com.github.pokemon.pokedex.ui.components.HomeOptionsComponent
import com.github.pokemon.pokedex.ui.components.SimpleRoundedSearch
import com.github.pokemon.pokedex.ui.components.model.HomeOptions
import com.github.pokemon.pokedex.ui.sharedsearch.SearchSharedViewModel
import com.github.pokemon.pokedex.ui.sharedsearch.SharedSearchAction
import kotlinx.collections.immutable.toImmutableList
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.viewmodel.koinActivityViewModel


@Composable
fun HomeRoute(
    onSearch: () -> Unit,
    sharedViewModel: SearchSharedViewModel = koinActivityViewModel(),
    viewModel: HomeViewModel = koinViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel, onSearch) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiEffect.collect { effect ->
                when (effect) {
                    //Todo Add pokedex feature
                    is HomeEffect.NavigateToPokedex -> {}
                    is HomeEffect.NavigateToSearch -> onSearchClick()
                }
            }
        }
    }
    HomeScreen(viewModel::onAction)
}

@Composable
fun HomeScreen(onAction: (HomeAction) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 32.dp, vertical = 32.dp),
    ) {
        item {
            Text(
                text = stringResource(id = R.string.main_app_bar_title),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                ),
                modifier = Modifier.padding(top = 48.dp, bottom = 24.dp),
            )
        }
        item {
            SimpleRoundedSearch(
                text = stringResource(id = R.string.search_bar_hint),
                onSearchClick = { onAction(HomeAction.OnSearchClick) },
            )
        }
        item {
            Spacer(modifier = Modifier.height(32.dp))
        }

        item {
            HomeOptionsComponent(options = HomeOptions.toImmutableList())
        }
    }
}


@Preview
@Composable
fun PreviewMainScreen() {
    HomeScreen({ })
}
