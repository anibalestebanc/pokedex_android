package com.github.zsoltk.pokedex.ui.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.github.zsoltk.pokedex.R
import com.github.zsoltk.pokedex.ui.components.HorizontalRuler
import com.github.zsoltk.pokedex.ui.components.LoadImage
import com.github.zsoltk.pokedex.domain.model.NewsItem

@Composable
fun NewsCard(newsItem: NewsItem) {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                NewsTitle(newsItem.title)
                NewsPublishedDate(newsItem.date)
            }

            NewsImage()
        }

        Box(modifier = Modifier.padding(vertical = 16.dp)) {
            HorizontalRuler(
                color = colorResource(id = R.color.grey_200)
            )
        }
    }
}

@Composable
private fun NewsImage() {
    Surface(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(start = 48.dp)
    ) {
        Box(modifier = Modifier.size(width = 112.dp, height = 64.dp)) {
            LoadImage(R.drawable.news1)
        }
    }
}

@Composable
private fun NewsTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.grey_900)
        ),
        maxLines = 2,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

@Composable
private fun NewsPublishedDate(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall.copy(
            color = colorResource(id = R.color.grey_500)
        )
    )
}
