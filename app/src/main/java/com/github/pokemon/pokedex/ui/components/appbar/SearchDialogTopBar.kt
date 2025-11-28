package com.github.pokemon.pokedex.ui.components.appbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.pokemon.pokedex.R
import com.github.pokemon.pokedex.theme.PokeAppTheme
import com.github.pokemon.pokedex.ui.components.SearchInputPillCompact

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchDialogTopBar(
    query: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    onSubmit: () -> Unit,
    onBackClick: () -> Unit,
    showBackButton: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        if (showBackButton) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(40.dp),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = stringResource(id = R.string.common_back),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp),
                )
            }
            Spacer(Modifier.width(4.dp))
        }

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
            placeholder = stringResource(id = R.string.search_bar_hint),
            onBackClick = {},
            onSubmit = {},
        )
    }
}


