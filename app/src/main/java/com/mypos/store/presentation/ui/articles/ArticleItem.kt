package com.mypos.store.presentation.ui.articles

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumTouchTargetEnforcement
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mypos.store.R
import com.mypos.store.domain.articles.model.ArticleEntity
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException


fun convertImageByteArrayToBitmap(imageData: ByteArray): Bitmap {

    return BitmapFactory.decodeByteArray(imageData, 0, imageData.size)

}

fun readImage(id: Int, path: String): Bitmap {
    val f: File = File(path, "$id.jpg")
    val b = BitmapFactory.decodeStream(FileInputStream(f))
    return b
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleItem(
    article: ArticleEntity,
    amountInCart: Int,
    cartButtonListener: (increase: Boolean, id: Int) -> Unit,
    onClickArticle: (id: Int) -> Unit,
    imagePath: String
) {
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
                onClickArticle.invoke(article.id)
            }
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                var image: Bitmap? = null
                try {
                    image = readImage(article.id, path = imagePath)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
                if (image == null) {
                    Image(
                        painter = painterResource(id = R.drawable.no_image),
                        contentDescription = "image",
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .width(100.dp)
                            .height(100.dp),
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    Image(
                        bitmap = image.asImageBitmap(),
                        contentDescription = "image",
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .width(100.dp)
                            .height(100.dp),
                        contentScale = ContentScale.Crop,
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 5.dp),
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
                            text = if (amountInCart != 0) (amountInCart * article.price).toString() + "$" else article.price.toString() + "$",
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
                            onClick = { cartButtonListener.invoke(true, article.id) },
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowUp,
                                contentDescription = "Cart",
                                tint = Color.Black
                            )
                        }
                        Text(text = amountInCart.toString())
                        IconButton(
                            onClick = { cartButtonListener.invoke(false, article.id) },
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