package com.github.pokemon.pokedex.ui.components.bottombar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
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
import com.github.pokemon.pokedex.R
import com.github.pokemon.pokedex.navigation.NavigationRoute
import com.github.pokemon.pokedex.navigation.TopDestination

@Composable
fun PokeBottomBar(
    current: NavKey?,
    onClick: (TopDestination) -> Unit,
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

            val defaultItemColor = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.onSurface,
                selectedTextColor = MaterialTheme.colorScheme.onSurface,
                unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                indicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            )

            NavigationBarItem(
                selected = current is NavigationRoute.Home,
                onClick = { onClick(TopDestination.HOME) },
                icon = {
                    Icon(
                        imageVector =  Icons.Outlined.Home,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.bottom_bar_home),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                alwaysShowLabel = true,
                colors = defaultItemColor,
            )

            NavigationBarItem(
                selected = current is NavigationRoute.SearchList,
                onClick = { onClick(TopDestination.SEARCH_LIST) },
                icon = {
                    Icon(
                        imageVector =  Icons.Outlined.Search,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.bottom_bar_search),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                alwaysShowLabel = true,
                colors = defaultItemColor,
            )

            NavigationBarItem(
                selected = current is NavigationRoute.Favorite,
                onClick = { onClick(TopDestination.FAVORITES) },
                icon = {
                    Icon(
                        imageVector =  Icons.Outlined.FavoriteBorder,
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.bottom_bar_favorite),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                },
                alwaysShowLabel = true,
                colors = defaultItemColor,
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PokeBottomBarPreview() {
    PokeBottomBar(
        current = NavigationRoute.SearchList(),
        onClick = {}
    )
}
