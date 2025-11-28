package com.github.pokemon.pokedex.ui.pokemondetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SectionTabs(
    tabs: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.background(Color.White)) {
        TabRow(
            selectedTabIndex = selectedIndex,
            containerColor = Color.White,
            contentColor = Color.Black,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier
                        .tabIndicatorOffset(tabPositions[selectedIndex])
                        .height(2.dp),
                    color = Color.Black
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = index == selectedIndex,
                    onClick = { onTabSelected(index) },
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Gray
                ) {
                    Text(
                        text = title,
                        modifier = Modifier.padding(vertical = 12.dp),
                        fontWeight = if (index == selectedIndex) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }
    }
}
