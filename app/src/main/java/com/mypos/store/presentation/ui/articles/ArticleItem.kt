package com.mypos.store.presentation.ui.articles

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumTouchTargetEnforcement
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mypos.store.R
import com.mypos.store.domain.articles.model.ArticleEntity


fun convertImageByteArrayToBitmap(imageData: ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleItem(
    article: ArticleEntity
) {
    val expanded = remember { mutableStateOf(false) }
    CompositionLocalProvider(
        LocalMinimumTouchTargetEnforcement provides false
    ) {
        Surface(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(120.dp),
            color = Color.White,
            shape = RoundedCornerShape(5.dp),

            shadowElevation = 9.dp,
            onClick = {
                expanded.value = !expanded.value
            }
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                if (article.image != null) {
                    Image(
                        bitmap = convertImageByteArrayToBitmap(imageData = article.image!!).asImageBitmap(),
                        contentDescription = "image",
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .width(100.dp)
                            .height(100.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(end = 5.dp)
                    ) {
                        Row {
                            Text(text = article.name, fontWeight = FontWeight.Bold)
                        }
                        Text(
                            fontSize = 12.sp,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            text = article.shortDescription
                        )
                        Text(
                            text = article.price.toString() + "$",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxHeight()
                                .wrapContentHeight()
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        IconButton(
                            onClick = { /*TODO*/ },
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowUp,
                                contentDescription = "Cart",
                                tint = Color.Black
                            )
                        }
                        Text(text = "1")
                        IconButton(
                            onClick = { /*TODO*/ },
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Cart",
                                tint = Color.Black
                            )
                        }
                    }

                }
            }
        }
    }

}