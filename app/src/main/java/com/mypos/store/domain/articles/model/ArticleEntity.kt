package com.mypos.store.domain.articles.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "articles_table")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String,
    val category: Int,
    var shortDescription: String,
    var fullDescription: String,
    var price: Double,
    val addDate: Date,
    var amountInCart: Int = 0
) {

    public fun cartAction(isIncreased: Boolean): Int {
        if (isIncreased) {
            amountInCart += 1
        } else {
            if (amountInCart != 0) {
                amountInCart -= 1
            }
        }
        return amountInCart
    }

    public fun removeFromCart() {
        amountInCart = 0
    }
}