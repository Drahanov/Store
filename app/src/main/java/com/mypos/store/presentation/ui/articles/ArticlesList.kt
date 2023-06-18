package com.mypos.store.presentation.ui.articles

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import com.mypos.store.domain.articles.model.ArticleEntity

@Composable
fun ArticlesList(
    articles: List<ArticleEntity>,

    ) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = articles) { article ->
           ArticleItem(article)
        }
    }
}