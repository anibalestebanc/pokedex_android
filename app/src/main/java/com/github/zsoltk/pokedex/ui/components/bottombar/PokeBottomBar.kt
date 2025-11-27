package com.github.zsoltk.pokedex.ui.components.bottombar

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PokeBottomBar(
    destinations: List<BottomBarItem>,
    selectedId: String?,
    onSelect: (String) -> Unit,
) {
    NavigationBar (
        containerColor = Color.White,
        tonalElevation = 3.dp
    ){
        destinations.forEach { dest ->
            NavigationBarItem(
                selected = selectedId == dest.id,
                onClick = { onSelect(dest.id) },
                icon = {
                    Icon(
                        dest.selectedIcon,
                        contentDescription = dest.label,
                    )
                },
                label = { Text(dest.label) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSurface,
                    selectedTextColor = MaterialTheme.colorScheme.onSurface,

                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface,

                    indicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )
            )
        }
    }
}

@Preview
@Composable
fun PokeBottomBarPreview(){
    PokeBottomBar(
        destinations = TopBottomBarDestinations,
        selectedId = "home",
        onSelect = {}
    )
}
