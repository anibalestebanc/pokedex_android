package com.github.zsoltk.pokedex.common

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.github.zsoltk.pokedex.appFontFamily

@Composable
fun Title(text: String, color: Color, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = TextStyle(
            fontFamily = appFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = color
        ),
        modifier = modifier
    )
}
