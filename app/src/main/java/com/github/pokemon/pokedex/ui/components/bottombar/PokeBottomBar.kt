package com.github.pokemon.pokedex.ui.components.bottombar

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PokeBottomBar(
    destinations: List<BottomBarItem>,
    selectedId: String?,
    onSelect: (String) -> Unit,
) {
    Column {
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
            thickness = 1.dp,
        )
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp,
        ) {
            destinations.forEach { dest ->
                val label = stringResource(id = dest.label)
                NavigationBarItem(
                    selected = selectedId == dest.id,
                    onClick = { onSelect(dest.id) },
                    icon = {
                        Icon(
                            dest.selectedIcon,
                            contentDescription = label,
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    },
                    label = {
                        Text(
                            text = label,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    },
                    alwaysShowLabel = true,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onSurface,
                        selectedTextColor = MaterialTheme.colorScheme.onSurface,

                        unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface,

                        indicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                    ),
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PokeBottomBarPreview() {
    PokeBottomBar(
        destinations = TopBottomBarDestinations,
        selectedId = "home",
        onSelect = {},
    )
}
