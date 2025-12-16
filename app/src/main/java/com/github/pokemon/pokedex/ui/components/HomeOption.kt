package com.github.pokemon.pokedex.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.pokemon.pokedex.R
import com.github.pokemon.pokedex.theme.PokeAppTheme
import com.github.pokemon.pokedex.ui.home.model.MenuItem

val HomeOptions = listOf(
    MenuItem.Pokedex,
    MenuItem.Moves,
    MenuItem.Abilities,
    MenuItem.Items,
    MenuItem.Locations,
    MenuItem.TypeCharts,
)
@Composable
fun HomeOptionsComponent(options: List<MenuItem> = HomeOptions) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        userScrollEnabled = false,
        modifier = Modifier.height(202.dp),
    ) {
        items(
            count = options.size,
            key = { index -> options[index].label },
        ) { index ->
            val option = options[index]
            HomeOption(
                text = stringResource(id = option.label),
                color = colorResource(option.colorResId),
                onClick = {},
            )
        }
    }
}
@Composable
fun HomeOption(text: String, color: Color, onClick: () -> Unit = {}) {
    Surface(
        color = color,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.clickable { onClick() },
    ) {
        Box(
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp),
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.White,
                ),
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 20.dp, y = 0.dp)
                    .size(96.dp),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pokeball_s),
                    contentDescription = null,
                    modifier = Modifier.alpha(0.15f),
                    colorFilter = ColorFilter.tint(Color.White),
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeOptionPreview() {
    PokeAppTheme(
        dynamicColor = false,
        darkTheme = false,
    ) {
        HomeOptionsComponent()
    }
}
