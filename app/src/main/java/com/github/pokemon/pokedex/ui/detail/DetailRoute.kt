package com.github.pokemon.pokedex.ui.detail

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.github.pokemon.pokedex.R
import com.github.pokemon.pokedex.domain.model.PokemonFullDetail
import com.github.pokemon.pokedex.theme.PokeAppTheme
import com.github.pokemon.pokedex.ui.components.common.RetryErrorScreen
import com.github.pokemon.pokedex.ui.components.common.LoadingScreen
import com.github.pokemon.pokedex.ui.components.utils.PokeBackgroundUtil.primaryTypeColorRes
import com.github.pokemon.pokedex.ui.detail.components.AboutSection
import com.github.pokemon.pokedex.ui.detail.components.BaseStatsSection
import com.github.pokemon.pokedex.ui.detail.components.DetailHeader
import com.github.pokemon.pokedex.ui.detail.components.SectionTabs
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailRoute(
    onBack: () -> Unit,
    id: String,
    viewModel: DetailViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isFavorite by viewModel.observeIsFavorite(id).collectAsStateWithLifecycle(initialValue = false)

    LaunchedEffect(id) {
        viewModel.onAction(DetailAction.OnStart(id))
    }

    LaunchedEffect(viewModel) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiEffect.collect { effect ->
                when (effect) {
                    is DetailUiEffect.ShareUrl -> {
                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, effect.url)
                        }
                        context.startActivity(
                            Intent.createChooser(intent, context.getString(R.string.share)),
                        )
                    }
                }
            }
        }
    }

    DetailScreen(
        pokemonId = id,
        isFavorite = isFavorite,
        uiState = uiState,
        onAction = viewModel::onAction,
        onBackClick = onBack,
    )
}

@Composable
fun DetailScreen(
    pokemonId: String,
    isFavorite: Boolean,
    uiState: DetailUiState,
    onAction: (DetailAction) -> Unit,
    onBackClick: () -> Unit,
) {
    when {
        uiState.isLoading -> LoadingScreen(message = stringResource(R.string.loading))
        uiState.errorMessage != null -> RetryErrorScreen(
            title = uiState.errorMessage,
            retryText = stringResource(R.string.retry),
            onRetry = { onAction(DetailAction.OnRetryDetailClick(pokemonId)) },
        )

        uiState.data != null -> PokemonDetailContent(
            data = uiState.data,
            isFavorite = isFavorite,
            onBackClick = onBackClick,
            onToggleFavorite = { onAction(DetailAction.OnToggleFavorite(pokemonId)) },
            onShareClick = { onAction(DetailAction.OnSharePokemon(it)) }
        )
    }
}

@Composable
fun PokemonDetailContent(
    data: PokemonFullDetail,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onToggleFavorite : () -> Unit,
    onShareClick : (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.surface),
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
            onShareClick = onShareClick
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
            0 -> AboutSection(data)
            1 -> BaseStatsSection(stats = data.stats)
        }
        Spacer(Modifier.height(16.dp))
    }
}

@Preview
@Composable
fun DetailScreenPreview() {
    PokeAppTheme {
         DetailScreen(
             "Pikachu"
             ,true
             ,DetailUiState()
             ,{}
             ,{}
         )
     }
}

