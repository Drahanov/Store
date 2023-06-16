package com.mypos.store.domain.article.model

data class ArticleEntity(
    val id: Int,
    val name: String,
    val category: Int,
    val shortDescription: String,
    val fullDescription: String,
    val price: Double,
    val addDate: Long,
    val image: Byte
)