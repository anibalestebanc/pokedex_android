package com.github.zsoltk.pokedex.pokedex

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
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
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.zsoltk.pokedex.R
import com.github.zsoltk.pokedex.appFontFamily
import com.github.zsoltk.pokedex.common.AsyncState
import com.github.zsoltk.pokedex.common.PokeBallBackground
import com.github.zsoltk.pokedex.common.PokeBallSmall
import com.github.zsoltk.pokedex.common.PokemonTypeLabels
import com.github.zsoltk.pokedex.common.RotateIndefinitely
import com.github.zsoltk.pokedex.common.TableRenderer
import com.github.zsoltk.pokedex.common.Title
import com.github.zsoltk.pokedex.common.TypeLabelMetrics.Companion.SMALL
import com.github.zsoltk.pokedex.entity.Pokemon
import com.github.zsoltk.pokedex.entity.PokemonApi
import com.github.zsoltk.pokedex.entity.PokemonLiveData
import com.github.zsoltk.pokedex.entity.color
import com.github.zsoltk.pokedex.entity.pokemons
import com.github.zsoltk.pokedex.lightThemeColors

interface PokemonList {

    companion object {
        @Composable
        fun Content(onPokemonSelected: (Pokemon) -> Unit) {
            val liveData = remember { PokemonLiveData() }
            val asyncState by liveData.observeAsState(AsyncState.Initialised())

            Box(modifier = Modifier.fillMaxSize()) {
                Surface(color = MaterialTheme.colors.surface) {
                    PokeBallBackground()
                }

                Crossfade(targetState = asyncState) { state ->
                    when (state) {
                        is AsyncState.Initialised,
                        is AsyncState.Loading -> LoadingView()
                        is AsyncState.Error -> ErrorView(onRetryClicked = { liveData.reload() })
                        is AsyncState.Result -> ContentView(state.result, onPokemonSelected)
                    }
                }
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
                style = MaterialTheme.typography.body1.copy(
                    color = colorResource(id = R.color.poke_red)
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(onClick = onRetryClicked) {
                Text("Retry")
            }
        }
    }
}

@Composable
private fun ContentView(pokemons: List<Pokemon>, onPokemonSelected: (Pokemon) -> Unit) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(32.dp)
    ) {
        Title(
            text = "Pokedex",
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.padding(
                top = 64.dp,
                bottom = 24.dp
            )
        )
        TableRenderer(cols = 2, cellSpacing = 4.dp, items = pokemons) { cell ->
            PokeDexCard(cell.item, onPokemonSelected)
        }
    }
}

@Composable
fun PokeDexCard(
    pokemon: Pokemon,
    onPokemonSelected: (Pokemon) -> Unit
) {
    Surface(
        color = colorResource(id = pokemon.color()),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.clickable{onPokemonSelected(pokemon) }
    ) {
        PokeDexCardContent(pokemon)
    }
}

@Composable
private fun PokeDexCardContent(pokemon: Pokemon) {
    Box(modifier = Modifier.height(120.dp).fillMaxWidth()) {
        Column(modifier = Modifier.align(Alignment.TopStart).padding(top = 8.dp, start = 12.dp)) {
            PokemonName(pokemon.name)
            PokemonTypeLabels(pokemon.typeOfPokemon, SMALL)
        }

        Box(modifier = Modifier.align(Alignment.TopEnd).padding(top = 8.dp, end = 12.dp)) {
            PokemonId(pokemon.id)
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = 5.dp, y = 10.dp)
                .size(96.dp)
        ) {
            PokeBallSmall(
                Color.White,
                0.25f
            )
        }

        pokemon.image?.let { image ->
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 8.dp, end = 8.dp)
                    .size(72.dp)
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
        style = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = colorResource(id = R.color.white_1000)
        ),
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun PokemonId(text: String?) {
    Text(
        text = text ?: "",
        modifier = Modifier.alpha(0.1f),
        style = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    )
}

@Preview
@Composable
private fun PreviewPokemonCard() {
    MaterialTheme(lightThemeColors) {
        PokeDexCard(pokemon = pokemons.first(), onPokemonSelected = {})
    }
}
