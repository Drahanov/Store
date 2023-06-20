package com.mypos.store.domain.articles.model

import androidx.room.ColumnInfo
import androidx.room.Entity
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
)