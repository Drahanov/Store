package com.mypos.store.presentation.ui.articles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.mypos.store.domain.articles.model.ArticleEntity

@Composable
fun ArticlesList(
    articles: List<ArticleEntity>,
    cartState: HashMap<Int, Int>,
    cartClickListener: (increase: Boolean, id: Int) -> Unit,
    onClickArticle: (id: Int) -> Unit
) {
    if (articles.isEmpty()) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "No products yet", textAlign = TextAlign.Center, fontSize = 24.sp)
        }
    } else {
        LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
            items(items = articles) { article ->
                if (cartState.containsKey(article.id)) {
                    cartState[article.id]?.let {
                        ArticleItem(
                            article,
                            it,
                            cartClickListener,
                            onClickArticle
                        )
                    }
                } else {
                    ArticleItem(article, 0, cartClickListener, onClickArticle)
                }
            }
        }
    }
}