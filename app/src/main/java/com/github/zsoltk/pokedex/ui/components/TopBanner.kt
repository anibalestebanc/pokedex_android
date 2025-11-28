package com.github.zsoltk.pokedex.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotInterested
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TopSnackbarBanner(
    text: String,
    showBanner: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(visible = showBanner) {
        Surface(color = MaterialTheme.colorScheme.onBackground) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = CenterVertically,
            ) {
                Text(
                    text = text,
                    modifier = Modifier
                        .padding(8.dp),
                    color = MaterialTheme.colorScheme.surface,
                    style = typography.bodyMedium,
                )
                IconButton(onClick = { }) {
                    Icon(
                        Icons.Default.NotInterested, contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.surface,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopSnackbarBannerPreview() {
    TopSnackbarBanner(
        text = "This is a preview",
        showBanner = true,
    )
}
