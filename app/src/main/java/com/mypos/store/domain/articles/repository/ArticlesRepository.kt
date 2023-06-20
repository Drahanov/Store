package com.mypos.store.domain.articles.repository

import android.graphics.Bitmap
import com.mypos.store.domain.articles.model.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface ArticlesRepository {
    suspend fun readAllArticles(): Flow<List<ArticleEntity>>
    suspend fun addArticle(articleEntity: ArticleEntity): Long
    suspend fun removeArticle(articleEntity: ArticleEntity)
    suspend fun updateArticle(articleEntity: ArticleEntity)
    suspend fun getArticleById(id: Int): Flow<ArticleEntity>
    fun saveToInternalStorage(bitmapImage: Bitmap, id: Int): String?
}