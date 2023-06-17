package com.mypos.store.domain.articles.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "articles_table")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val category: Int,
    val shortDescription: String,
    val fullDescriptwion: String,
    val price: Double,
    val addDate: Date,
    val image: Byte
)