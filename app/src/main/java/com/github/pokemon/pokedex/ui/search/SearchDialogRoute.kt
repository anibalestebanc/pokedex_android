package com.github.pokemon.pokedex.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.History
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.pokemon.pokedex.R
import com.github.pokemon.pokedex.ui.components.appbar.SearchDialogTopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchDialogRoute(
    navigateToResult: Boolean = false,
    initialQuery: String = "",
    onBackClick: () -> Unit,
    onSearchResult: (String) -> Unit,
    onBackAndSaveStateHandle: (String) -> Unit,
    viewModel: SearchViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.onAction(SearchAction.OnStart)
    }

    LaunchedEffect(initialQuery) {
        if (initialQuery.isNotEmpty()) {
            viewModel.onAction(SearchAction.SetInitialQuery(initialQuery))
        }
    }

    SearchDialogScreen(
        uiState = uiState,
        onDismiss = { onBackClick() },
        onSubmitResult = { q ->
            when (navigateToResult) {
                true -> onSearchResult(q)
                false -> onBackAndSaveStateHandle(q)
            }
        },
        onAction = viewModel::onAction,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchDialogScreen(
    uiState: SearchUiState,
    onDismiss: () -> Unit,
    onSubmitResult: (String) -> Unit,
    onAction: (SearchAction) -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme
    val shapes = MaterialTheme.shapes
    val typography = MaterialTheme.typography

    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Dialog(
        onDismissRequest = {
            focusManager.clearFocus(force = true)
            keyboard?.hide()
            onDismiss()
        },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        MaterialTheme(colorScheme = colorScheme, typography = typography, shapes = shapes) {
            Surface {
                Scaffold(
                    topBar = {
                        SearchDialogTopBar(
                            query = uiState.query,
                            onValueChange = { onAction(SearchAction.QueryChanged(it)) },
                            placeholder = stringResource(id = R.string.search_bar_hint),
                            onSubmit = {
                                val query = uiState.query.trim()
                                if (query.isNotEmpty()) {
                                    onAction(SearchAction.SearchSubmit)
                                }
                                onSubmitResult(query)
                            },
                            onBackClick = { onDismiss() },
                        )
                    },
                    containerColor = MaterialTheme.colorScheme.surface,
                ) { inner ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(inner),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                stringResource(id = R.string.search_dialog_recent_searches),
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.titleSmall,
                            )
                            TextButton(
                                onClick = { onAction(SearchAction.RemoveAllHistory) },
                            ) { Text(
                                text = stringResource(id = R.string.search_dialog_clear),
                                color = MaterialTheme.colorScheme.onSurface)
                            }
                        }

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(bottom = 24.dp),
                        ) {
                            items(
                                count = uiState.searchHistory.size,
                                key = { it },
                            ) { index ->
                                val item = uiState.searchHistory[index]

                                ListItem(
                                    headlineContent = { Text(item, color = MaterialTheme.colorScheme.onSurface) },
                                    leadingContent = {
                                        Icon(
                                            Icons.Rounded.History,
                                            stringResource(id = R.string.search_dialog_history_icon_content_description),
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            onAction(SearchAction.QueryChanged(item))
                                            onAction(SearchAction.SearchSubmit)
                                            onSubmitResult(item)
                                        },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
