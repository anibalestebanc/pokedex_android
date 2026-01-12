package com.github.pokemon.pokedex.ui.components.bottombar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import com.github.pokemon.pokedex.navigation.NavigationRoute

@Composable
fun PokeBottomBar(
    current: NavKey,
    destinations: Map<String, BottomBarItem>,
    onClick: (NavKey) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column (modifier = modifier.fillMaxWidth()){
        HorizontalDivider(
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
            thickness = 1.dp,
        )
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp,
        ) {

            destinations.entries.forEach { map ->
                val item = map.value
                val label = stringResource(id = item.label)
                NavigationBarItem(
                    selected = current::class == item.route::class,
                    onClick = { onClick(item.route) },
                    icon = {
                        Icon(
                            imageVector =  item.selectedIcon,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = null,
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
        current = NavigationRoute.SearchList,
        destinations = Destinations,
        onClick = {}
    )
}
