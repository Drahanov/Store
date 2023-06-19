package com.mypos.store.presentation.ui.cart

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import com.mypos.store.domain.articles.model.ArticleEntity
import com.mypos.store.presentation.ui.articles.ArticleItem

@Composable
fun ArticlesList(
    articles: List<ArticleEntity>,
    cartState: HashMap<Int, Int>,
    cartClickListener: (increase: Boolean, id: Int) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = articles) { article ->
            if (cartState.containsKey(article.id)) {
                cartState[article.id]?.let { ArticleItem(article, it, cartClickListener) }
            }
        }
    }
}