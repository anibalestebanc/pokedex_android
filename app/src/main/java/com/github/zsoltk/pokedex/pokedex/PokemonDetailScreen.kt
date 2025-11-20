package com.github.zsoltk.pokedex.pokedex

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.zsoltk.pokedex.R
import com.github.zsoltk.pokedex.appFontFamily
import com.github.zsoltk.pokedex.common.LoadImage
import com.github.zsoltk.pokedex.common.PokeBallLarge
import com.github.zsoltk.pokedex.common.PokemonTypeLabels
import com.github.zsoltk.pokedex.common.Rotate
import com.github.zsoltk.pokedex.common.RotateIndefinitely
import com.github.zsoltk.pokedex.common.Title
import com.github.zsoltk.pokedex.common.TypeLabelMetrics.Companion.MEDIUM
import com.github.zsoltk.pokedex.entity.Pokemon
import com.github.zsoltk.pokedex.entity.color
import com.github.zsoltk.pokedex.entity.pokemons
import com.github.zsoltk.pokedex.pokedex.section.AboutSection
import com.github.zsoltk.pokedex.pokedex.section.BaseStatsSection
import com.github.zsoltk.pokedex.pokedex.section.EvolutionSection
import com.github.zsoltk.pokedex.pokedex.section.MovesSection

@Composable
fun PokemonDetails(pokemon: Pokemon) {
    Surface(color = colorResource(id = pokemon.color())) {
        Box {
            RoundedRectangleDecoration()
            DottedDecoration()
            RotatingPokeBall()
            HeaderLeft(pokemon)
            HeaderRight(pokemon)
            CardContent(pokemon)
            PokemonImage(pokemon)
        }
    }
}

@Composable
private fun BoxScope.RoundedRectangleDecoration() {
    Box(
        modifier = Modifier
            .align(Alignment.TopStart)
            .offset(x = (-60).dp, y = (-50).dp)
    ) {
        Rotate(-20f) {
            Surface(color = Color(0x22FFFFFF), shape = RoundedCornerShape(32.dp)) {
                Spacer(modifier = Modifier.size(150.dp))
            }
        }
    }
}

@Composable
private fun BoxScope.DottedDecoration() {
    Box(
        modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(top = 4.dp, end = 100.dp)
            .size(width = 63.dp, height = 34.dp)
    ) {
        LoadImage(imageResId = R.drawable.dotted, opacity = 0.3f)
    }
}

@Composable
private fun BoxScope.RotatingPokeBall() {
    Box(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 140.dp)
            .size(200.dp)
    ) {
        RotateIndefinitely(durationPerRotation = 4000) {
            PokeBallLarge(
                tint = colorResource(id = R.color.grey_100),
                opacity = 0.25f
            )
        }
    }
}

@Composable
private fun BoxScope.HeaderRight(pokemon: Pokemon) {
    Box(
        modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(top = 52.dp, end = 32.dp)
    ) {
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = pokemon.id ?: "",
                style = TextStyle(
                    fontFamily = appFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = pokemon.category ?: "",
                style = TextStyle(
                    fontFamily = appFontFamily,
                    fontSize = 12.sp,
                    color = Color.White
                )
            )
        }
    }
}

@Composable
private fun BoxScope.HeaderLeft(pokemon: Pokemon) {
    Box(
        modifier = Modifier
            .align(Alignment.TopStart)
            .padding(top = 40.dp, start = 32.dp)
    ) {
        Column {
            Title(
                text = pokemon.name ?: "",
                color = Color.White
            )

            pokemon.typeOfPokemon?.let {
                Row {
                    PokemonTypeLabels(it, MEDIUM)
                }
            }
        }
    }
}

private enum class Sections(val title: String) {
    About("About"),
    BaseStats("Base stats"),
    Evolution("Evolution"),
    Moves("Moves")
}

@Composable
private fun BoxScope.CardContent(pokemon: Pokemon) {
    Box(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 300.dp)
    ) {
        Surface(shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)) {
            Column(modifier = Modifier.fillMaxSize()) {

                Spacer(modifier = Modifier.height(32.dp))

                val sectionTitles = Sections.values().map { it.title }
                var selectedTabIndex by remember { mutableStateOf(Sections.BaseStats.ordinal) }
                TabRow(selectedTabIndex = selectedTabIndex) {
                    sectionTitles.forEachIndexed { index, text ->
                        Tab(
                            text = { Text(text) },
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index }
                        )
                    }
                }

                Box(modifier = Modifier.padding(24.dp)) {
                    when (Sections.values()[selectedTabIndex]) {
                        Sections.About -> AboutSection(pokemon)
                        Sections.BaseStats -> BaseStatsSection(pokemon)
                        Sections.Evolution -> EvolutionSection(pokemon)
                        Sections.Moves -> MovesSection(pokemon)
                    }
                }
            }
        }
    }
}

@Composable
private fun BoxScope.PokemonImage(pokemon: Pokemon) {
    pokemon.image?.let { image ->
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 140.dp)
                .size(200.dp)
        ) {
            LoadImage(image)
        }
    }
}


@Preview
@Composable
private fun PreviewPokemonDetails() {
    Box {
        PokemonDetails(pokemons.first())
    }
}
