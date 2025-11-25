package com.github.zsoltk.pokedex.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSuggestions(
    historySearch: List<String>,
    onSelect: (String) -> Unit,
    onRemove: (String) -> Unit
) {
    Column {
        if (historySearch.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Búsquedas recientes", style = MaterialTheme.typography.labelLarge)
            }
        }
        historySearch.forEach { q ->
            ListItem(
                headlineContent = { Text(q, style = MaterialTheme.typography.labelMedium) },
                leadingContent = { Icon(Icons.Default.History, contentDescription = null) },
                trailingContent = {
                    IconButton(onClick = { onRemove(q) }) {
                        Icon(Icons.Default.Close, contentDescription = "Eliminar")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelect(q) }
                    .padding(horizontal = 8.dp)
            )
            Divider()
        }
        if (historySearch.isEmpty()) {
            ListItem(
                headlineContent = { Text("Sin búsquedas recientes", style = MaterialTheme.typography.labelLarge) },
                leadingContent = { Icon(Icons.Default.History, contentDescription = null) }
            )
        }
    }
}
