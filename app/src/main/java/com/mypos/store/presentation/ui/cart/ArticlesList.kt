package com.mypos.store.presentation.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.mypos.store.R
import com.mypos.store.domain.articles.model.ArticleEntity

@Composable
fun ArticlesList(
    articles: List<ArticleEntity>,
    cartClickListener: (increase: Boolean, article: ArticleEntity) -> Unit,
    imagePath: String
) {
    val articlesInCart = articles.filter {
        it.amountInCart > 0
    }
    if (articlesInCart.isEmpty()) {

        Box(
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_empty_cart),
                    contentDescription = "image",
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp),
                    alignment = Alignment.Center
                )
                Text(
                    text = "Cart is empty",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                )
            }
        }
    } else {
        LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
            items(items = articlesInCart) { article ->
                ArticleItem(
                    article,
                    cartClickListener,
                    {},
                    imagePath
                )
            }
        }
    }
}

