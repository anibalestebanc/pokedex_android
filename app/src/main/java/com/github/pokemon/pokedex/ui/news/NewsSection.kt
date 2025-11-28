package com.github.pokemon.pokedex.ui.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.pokemon.pokedex.R
import com.github.pokemon.pokedex.domain.model.NewsItem

@Preview
@Composable
fun NewsSection() {
    Column {
        NewsHeaderSection()
        Spacer(modifier = Modifier.height(32.dp))
        NewsList()
    }
}

@Composable
fun NewsHeaderSection() {
    Box(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.align(Alignment.BottomStart)) {
            NewsHeader()
        }

        Box(modifier = Modifier.align(Alignment.BottomEnd)) {
            NewsViewAll()
        }
    }
}

@Composable
fun NewsHeader() {
    Text(
        text = stringResource(id = R.string.news_section_title),
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.W900
        )
    )
}

@Composable
fun NewsViewAll() {
    Text(
        text = stringResource(id = R.string.news_section_view_all),
        style = MaterialTheme.typography.bodyMedium.copy(
            color = colorResource(id = R.color.poke_blue)
        )
    )
}

@Composable
fun NewsList() {
    val news = listOf(
        NewsItem(),
        NewsItem(),
        NewsItem(),
        NewsItem(),
        NewsItem(),
        NewsItem(),
        NewsItem()
    )

    news.forEach {
        NewsCard(it)
    }
}
