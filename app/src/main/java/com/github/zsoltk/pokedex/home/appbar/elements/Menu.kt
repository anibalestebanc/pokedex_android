package com.github.zsoltk.pokedex.home.appbar.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.zsoltk.pokedex.common.PokeBallSmall
import com.github.zsoltk.pokedex.common.TableRenderer
import com.github.zsoltk.pokedex.home.Home.MenuItem
import com.github.zsoltk.pokedex.home.Home.MenuItem.Abilities
import com.github.zsoltk.pokedex.home.Home.MenuItem.Items
import com.github.zsoltk.pokedex.home.Home.MenuItem.Locations
import com.github.zsoltk.pokedex.home.Home.MenuItem.Moves
import com.github.zsoltk.pokedex.home.Home.MenuItem.Pokedex
import com.github.zsoltk.pokedex.home.Home.MenuItem.TypeCharts

@Composable
fun Menu(onMenuItemSelected: (MenuItem) -> Unit) {
    val menuItems = listOf(
        Pokedex, Moves,
        Abilities, Items,
        Locations, TypeCharts
    )

    TableRenderer(cols = 2, cellSpacing = 5.dp, items = menuItems) { cell ->
        MenuItemButton(
            text = cell.item.label,
            color = colorResource(cell.item.colorResId),
            onClick = { onMenuItemSelected(cell.item) }
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
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp),
                text = text,
                style = MaterialTheme.typography.body1.copy(
                    color = Color.White
                )
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = (-30).dp, y = (-40).dp)
                    .size(60.dp),
            ) {
                PokeBallSmall(
                    Color.White,
                    0.15f
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 20.dp, y = 0.dp)
                    .size(96.dp)
            ) {
                PokeBallSmall(
                    Color.White,
                    0.15f
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
