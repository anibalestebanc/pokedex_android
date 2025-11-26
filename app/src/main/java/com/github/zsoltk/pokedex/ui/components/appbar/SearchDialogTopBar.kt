package com.github.zsoltk.pokedex.ui.components.appbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.zsoltk.pokedex.theme.PokeAppTheme
import com.github.zsoltk.pokedex.ui.components.SearchInputPillCompact

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchDialogTopBar(
    query: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    onSubmit: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        /*     IconButton(
                 onClick = onBack,
                 modifier = Modifier.size(40.dp),
             ) {
                 Icon(
                     imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                     contentDescription = "Back",
                     tint = MaterialTheme.colorScheme.onSurface,
                     modifier = Modifier.size(24.dp),
                 )
             }
             Spacer(Modifier.width(4.dp))
         */

        SearchInputPillCompact(
            value = query,
            onValueChange = onValueChange,
            placeholder = placeholder,
            onSubmit = onSubmit,
            requestFocus = true,
        )
    }
}

@Preview
@Composable
fun SearchFullscreenTopBarPreview() {
    PokeAppTheme {
        SearchDialogTopBar(
            query = "",
            onValueChange = {},
            placeholder = "Search Pokémon",
            onBack = {},
            onSubmit = {},
        )
    }
}


