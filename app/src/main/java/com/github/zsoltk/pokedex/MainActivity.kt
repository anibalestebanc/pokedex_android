package com.github.zsoltk.pokedex

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colors = lightThemeColors,
                typography = themeTypography
            ) {
                Root.Content()
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme(
        colors = lightThemeColors,
        typography = themeTypography
    ) {
        Root.Content()
    }
}
