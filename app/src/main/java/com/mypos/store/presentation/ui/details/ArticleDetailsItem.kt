package com.mypos.store.presentation.ui.details

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.mypos.store.presentation.ui.articles.readImage
import java.io.FileNotFoundException


fun convertImageByteArrayToBitmap(imageData: ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailsItem(
    article: ArticleEntity,
    amountInCart: Int,
    cartButtonListener: (increase: Boolean, id: Int) -> Unit,
    onDeleteClick: (article: ArticleEntity) -> Unit,
    onUpdateClick: (id: Int) -> Unit,
    imagePath: String
) {
    CompositionLocalProvider(
        LocalMinimumTouchTargetEnforcement provides false
    ) {
        Surface(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            color = Color.White,
            shape = RoundedCornerShape(5.dp),
            shadowElevation = 9.dp,
        ) {
            Column(
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
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f),
                        contentScale = ContentScale.Crop,
                    )
                } else {
                    Image(
                        bitmap = image.asImageBitmap(),
                        contentDescription = "image",
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f),
                        contentScale = ContentScale.Crop,
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight(),
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(end = 5.dp),
                        verticalArrangement = Arrangement.SpaceBetween

                    ) {
                        Column {
                            Text(
                                text = article.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                            Text(
                                fontSize = 15.sp,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                                text = article.shortDescription
                            )
                        }


                        Column {

                            Text(
                                text = "Description",
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                            Text(
                                fontSize = 14.sp,
                                maxLines = 6,
                                overflow = TextOverflow.Ellipsis,
                                text = article.fullDescription
                            )
                        }

                        Text(
                            text = if (amountInCart != 0) (amountInCart * article.price).toString() + "$" else article.price.toString() + "$",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = { cartButtonListener.invoke(false, article.id) },
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Cart",
                                tint = Color.Black
                            )
                        }
                        Text(text = amountInCart.toString())
                        IconButton(
                            onClick = { cartButtonListener.invoke(true, article.id) },
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "Cart",
                                tint = Color.Black
                            )
                        }
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                            onClick = {
                                onUpdateClick(article.id)
                            }) {
                            Text(text = "Edit")
                        }
                        Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                            onClick = {
                                onDeleteClick(article)
                            }) {
                            Text(text = "Delete")
                        }
                    }

                }
            }
        }
    }

}