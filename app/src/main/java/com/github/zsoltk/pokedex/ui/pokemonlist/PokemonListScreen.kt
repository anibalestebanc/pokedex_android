package com.github.zsoltk.pokedex.ui.pokemonlist

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.zsoltk.pokedex.R
import com.github.zsoltk.pokedex.ui.components.AsyncState
import com.github.zsoltk.pokedex.ui.components.PokeBallBackground
import com.github.zsoltk.pokedex.ui.components.PokeBallSmall
import com.github.zsoltk.pokedex.ui.components.PokemonTypeLabels
import com.github.zsoltk.pokedex.ui.components.RotateIndefinitely
import com.github.zsoltk.pokedex.ui.components.TableRenderer
import com.github.zsoltk.pokedex.ui.components.Title
import com.github.zsoltk.pokedex.ui.components.TypeLabelMetrics.Companion.SMALL
import com.github.zsoltk.pokedex.domain.model.Pokemon
import com.github.zsoltk.pokedex.data.datasource.remote.PokemonApi
import com.github.zsoltk.pokedex.ui.PokemonLiveData
import com.github.zsoltk.pokedex.domain.model.color
import com.github.zsoltk.pokedex.domain.model.pokemons
import com.github.zsoltk.pokedex.theme.PokeAppTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun PokemonListRoute(
    onBackClick: () -> Unit,
    onPokemonDetailClick: (String) -> Unit,
    viewModel: PokemonListViewModel = koinViewModel(),
) {
    val liveData = remember { PokemonLiveData() }
    val asyncState by liveData.observeAsState(AsyncState.Initialised())
    PokemonListScreen(
        onPokemonSelected = { pokemon ->
            onPokemonDetailClick(pokemon.name ?: "")
        },
        asyncState = asyncState,
        reloaded = { liveData.reload() },
    )
}

@Composable
fun PokemonListScreen(
    onPokemonSelected: (Pokemon) -> Unit,
    asyncState: AsyncState<List<Pokemon>>,
    reloaded: () -> Unit,
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            PokeBallBackground()
        }

        Crossfade(targetState = asyncState, label = "") {
            when (it) {
                is AsyncState.Initialised,
                is AsyncState.Loading,
                    -> LoadingView()

                is AsyncState.Error -> ErrorView(onRetryClicked = { reloaded() })
                is AsyncState.Result -> ContentView(it.result, onPokemonSelected)
            }
        }
    }
}

@Composable
private fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        RotateIndefinitely(durationPerRotation = 400) {
            Box(modifier = Modifier.size(50.dp)) {
                PokeBallSmall(tint = colorResource(id = R.color.poke_light_red))
            }
        }
    }
}

@Composable
private fun ErrorView(onRetryClicked: () -> Unit) {
    val errorRatio = "%.0f".format(PokemonApi.randomFailureChance * 100)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = buildAnnotatedString {
                    append("There's a $errorRatio% chance of a simulated error.\nNow it happened.")
                    addStyle(ParagraphStyle(textAlign = TextAlign.Center), 0, length)
                },
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = colorResource(id = R.color.poke_red),
                ),
                modifier = Modifier.padding(bottom = 16.dp),
            )
            Button(onClick = onRetryClicked) {
                Text(stringResource(R.string.retry))
            }
        }
    }
}

@Composable
private fun ContentView(pokemons: List<Pokemon>, onPokemonSelected: (Pokemon) -> Unit) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(32.dp),
    ) {
        Title(
            text = stringResource(R.string.pokedex_title),
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(
                top = 64.dp,
                bottom = 24.dp,
            ),
        )
        TableRenderer(cols = 2, cellSpacing = 4.dp, items = pokemons) { cell ->
            PokeDexCard(cell.item, onPokemonSelected)
        }
    }
}

@Composable
fun PokeDexCard(
    pokemon: Pokemon,
    onPokemonSelected: (Pokemon) -> Unit,
) {
    Surface(
        color = colorResource(id = pokemon.color()),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.clickable { onPokemonSelected(pokemon) },
    ) {
        PokeDexCardContent(pokemon)
    }
}

@Composable
private fun PokeDexCardContent(pokemon: Pokemon) {
    Box(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 8.dp, start = 12.dp),
        ) {
            PokemonName(pokemon.name)
            PokemonTypeLabels(pokemon.typeOfPokemon, SMALL)
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 8.dp, end = 12.dp),
        ) {
            PokemonId(pokemon.id)
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 5.dp, y = 10.dp)
                .size(96.dp),
        ) {
            PokeBallSmall(
                Color.White,
                0.25f,
            )
        }

        pokemon.image?.let { image ->
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 8.dp, end = 8.dp)
                    .size(72.dp),
            ) {
                Image(painter = painterResource(id = image), contentDescription = pokemon.name)
            }
        }
    }
}

@Composable
private fun PokemonName(text: String?) {
    Text(
        text = text ?: "",
        style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.Bold,
        ),
        modifier = Modifier.padding(bottom = 8.dp),
    )
}

@Composable
private fun PokemonId(text: String?) {
    Text(
        text = text ?: "",
        modifier = Modifier.alpha(0.1f),
        style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Bold,
        ),
    )
}

@Preview
@Composable
private fun PreviewPokemonCard() {
    PokeAppTheme {
        PokeDexCard(pokemon = pokemons.first(), onPokemonSelected = {})
    }
}
