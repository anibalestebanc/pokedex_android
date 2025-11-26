package com.github.zsoltk.pokedex.ui.fullsearch

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.zsoltk.pokedex.ui.components.appbar.SearchDialogTopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchDialogRoute(
    navigateToResult: Boolean = false,
    initialQuery: String = "",
    onBackClick: () -> Unit,
    onSearchResult: (String) -> Unit,
    saveOnStateHandle: (String) -> Unit,
    viewModel: SearchFullViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(SearchFullEvent.OnStart)
    }

    LaunchedEffect(initialQuery) {
        if (initialQuery.isNotEmpty()) {
            viewModel.onEvent(SearchFullEvent.SetInitialQuery(initialQuery))
        }
    }

    SearchFullscreenDialog(
        uiState = uiState,
        onDismiss = { onBackClick() },
        onSubmitResult = { query ->
            saveOnStateHandle(query)
            if (navigateToResult) {
                onSearchResult(query)
            } else {
                onBackClick()
            }
        },
        onEvent = viewModel::onEvent,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchFullscreenDialog(
    uiState: SearchFullUiState,
    onDismiss: () -> Unit,
    onSubmitResult: (String) -> Unit,
    onEvent: (SearchFullEvent) -> Unit,
) {

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
        Surface(color = MaterialTheme.colorScheme.background) {
            Scaffold(
                topBar = {
                    SearchDialogTopBar(
                        query = uiState.query,
                        onValueChange = { onEvent(SearchFullEvent.QueryChanged(it)) },
                        placeholder = "Search Pokemon",
                        onSubmit = {
                            val query = uiState.query.trim()
                            if (query.isNotEmpty()) {
                                onEvent(SearchFullEvent.SearchSubmit)
                            }
                            onSubmitResult(query)
                        },
                        onBack = { onDismiss() },
                    )
                },
            ) { inner ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(inner),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text("Búsquedas recientes", style = MaterialTheme.typography.titleSmall)
                        TextButton(
                            onClick = { onEvent(SearchFullEvent.RemoveAllHistory) },
                        ) { Text("Limpiar") }
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
                                headlineContent = { Text(item) },
                                leadingContent = { Icon(Icons.Rounded.History, null) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        onEvent(SearchFullEvent.QueryChanged(item))
                                        onEvent(SearchFullEvent.SearchSubmit)
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
