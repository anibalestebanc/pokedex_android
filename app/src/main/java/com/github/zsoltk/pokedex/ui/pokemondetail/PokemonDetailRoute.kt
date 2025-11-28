package com.github.zsoltk.pokedex.ui.pokemondetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.zsoltk.pokedex.R
import com.github.zsoltk.pokedex.domain.model.PokemonFullDetail
import com.github.zsoltk.pokedex.theme.PokeAppTheme
import com.github.zsoltk.pokedex.ui.components.common.ErrorWithRetryScreen
import com.github.zsoltk.pokedex.ui.components.common.LoadingScreen
import com.github.zsoltk.pokedex.ui.components.utils.PokeBackgroundUtil.primaryTypeColorRes
import com.github.zsoltk.pokedex.ui.pokemondetail.components.AboutSectionV2
import com.github.zsoltk.pokedex.ui.pokemondetail.components.BaseStatsSectionV2
import com.github.zsoltk.pokedex.ui.pokemondetail.components.DetailHeader
import com.github.zsoltk.pokedex.ui.pokemondetail.components.SectionTabs
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonDetailRoute(
    onBackClick: () -> Unit,
    pokemonId: String,
    viewModel: PokemonDetailViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isFavorite by viewModel.observeIsFavorite(pokemonId).collectAsStateWithLifecycle(initialValue = false)

    LaunchedEffect(pokemonId) {
        viewModel.onEvent(DetailEvent.OnStart(pokemonId))
    }

    PokemonDetailScreenV2(
        pokemonId = pokemonId,
        isFavorite = isFavorite,
        state = uiState,
        onEvent = viewModel::onEvent,
        onBackClick = onBackClick,
    )
}

@Composable
fun PokemonDetailScreenV2(
    pokemonId: String,
    isFavorite: Boolean,
    state: DetailUiState,
    onEvent: (DetailEvent) -> Unit,
    onBackClick: () -> Unit,
) {
    when {
        state.isLoading && state.data == null -> LoadingScreen(message = stringResource(R.string.loading))
        state.error != null && state.data == null -> ErrorWithRetryScreen(
            title = stringResource(state.error),
            retryText = stringResource(R.string.retry),
            onRetry = { onEvent(DetailEvent.OnRetryClick(pokemonId)) },
        )

        state.data != null -> PokemonDetailContent(
            data = state.data,
            isFavorite = isFavorite,
            onRefresh = { onEvent.invoke(DetailEvent.OnRetryClick(pokemonId)) },
            onBackClick = onBackClick,
            onToggleFavorite = { onEvent(DetailEvent.OnToggleFavorite(pokemonId)) },
        )
    }
}

@Composable
fun PokemonDetailContent(
    data: PokemonFullDetail,
    isFavorite: Boolean,
    onRefresh: () -> Unit,
    onBackClick: () -> Unit,
    onToggleFavorite : () -> Unit,
    modifier: Modifier = Modifier,
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.White),
    ) {
        DetailHeader(
            name = data.name,
            numberLabel = data.numberLabel,
            genera = data.genera,
            types = data.types,
            imageUrl = data.imageUrl,
            isFavorite = isFavorite,
            headerColor = colorResource(id = primaryTypeColorRes(data.types)),
            onBackClick = onBackClick,
            onToggleFavorite = onToggleFavorite,
        )

        // Tabs
        var selectedTab by remember { mutableStateOf(0) }
        SectionTabs(
            tabs = listOf(
                stringResource(R.string.pokemon_detail_about),
                stringResource(R.string.pokemon_detail_base_stats),
            ),
            selectedIndex = selectedTab,
            onTabSelected = { selectedTab = it },
        )

        when (selectedTab) {
            0 -> AboutSectionV2(data)
            1 -> BaseStatsSectionV2(stats = data.stats)
        }
        Spacer(Modifier.height(16.dp))
    }
}

@Preview
@Composable
fun PokemonDetailScreenV2Preview() {
    PokeAppTheme {
         PokemonDetailScreenV2(
             "Pikachu"
             ,true
             ,DetailUiState()
             ,{}
             ,{}
         )
     }
}

