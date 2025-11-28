package com.github.pokemon.pokedex.ui.components.appbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.pokemon.pokedex.ui.components.PokeBallSmall
import com.github.pokemon.pokedex.ui.components.TableRenderer
import com.github.pokemon.pokedex.ui.home.model.MenuItem

@Composable
fun Menu(onMenuItemSelected: (MenuItem) -> Unit) {
    val menuItems = listOf(
        MenuItem.Pokedex, MenuItem.Moves,
        MenuItem.Abilities, MenuItem.Items,
        MenuItem.Locations, MenuItem.TypeCharts,
    )

    TableRenderer(cols = 2, cellSpacing = 5.dp, items = menuItems) { cell ->
        MenuItemButton(
            text = stringResource(id = cell.item.label),
            color = colorResource(cell.item.colorResId),
            onClick = { onMenuItemSelected(cell.item) },
        )
    }
}

@Composable
fun MenuItemButton(text: String, color: Color, onClick: () -> Unit = {}) {
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
                    .align(Alignment.TopStart)
                    .offset(x = (-30).dp, y = (-40).dp)
                    .size(60.dp),
            ) {
                PokeBallSmall(
                    Color.White,
                    0.15f,
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 20.dp, y = 0.dp)
                    .size(96.dp),
            ) {
                PokeBallSmall(
                    Color.White,
                    0.15f,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewMenu() {
    Menu {}
}
