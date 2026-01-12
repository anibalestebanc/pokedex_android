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
import com.github.pokemon.pokedex.ui.components.appbar.RoundedSearchTopBar
import com.github.pokemon.pokedex.ui.sharedsearch.SearchSharedViewModel
import com.github.pokemon.pokedex.ui.sharedsearch.SharedSearchAction
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.viewmodel.koinActivityViewModel

@Composable
fun SearchDialogRoute(
    onBackClick: () -> Unit,
    onSearchList: (query: String) -> Unit,
    sharedViewModel: SearchSharedViewModel = koinActivityViewModel(),
    viewModel: SearchViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sharedQuery = sharedViewModel.sharedQuery.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.onAction(SearchAction.OnStart)
    }

    SearchDialogScreen(
        uiState = uiState,
        searchQuery = sharedQuery.value,
        onQueryChange = { query -> sharedViewModel.onAction(SharedSearchAction.QueryChanged(query)) },
        onDismiss = { onBackClick() },
        onSubmitResult = { query -> onSearchList(query) },
        onAction = viewModel::onAction,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchDialogScreen(
    uiState: SearchUiState,
    searchQuery : String,
    onQueryChange: (String) -> Unit,
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
                        RoundedSearchTopBar(
                            query = searchQuery,
                            onValueChange = onQueryChange,
                            placeholder = stringResource(id = R.string.search_bar_hint),
                            onSubmit = {
                                val query = searchQuery.trim()
                                if (query.isNotEmpty()) {
                                    onAction(SearchAction.SearchSubmit(query))
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
                                count = uiState.historySearch.size,
                                key = { it },
                            ) { index ->
                                val savedQuery = uiState.historySearch[index]

                                ListItem(
                                    headlineContent = { Text(savedQuery, color = MaterialTheme.colorScheme.onSurface) },
                                    leadingContent = {
                                        Icon(
                                            Icons.Rounded.History,
                                            stringResource(id = R.string.search_dialog_history_icon_content_description),
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            onQueryChange(savedQuery)
                                            onAction(SearchAction.SearchSubmit(savedQuery))
                                            onSubmitResult(savedQuery)
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
