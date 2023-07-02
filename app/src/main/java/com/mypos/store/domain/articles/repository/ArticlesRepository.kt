package com.mypos.store.domain.articles.repository

import android.graphics.Bitmap
import com.mypos.store.data.articles.repository.ArticlesRepositoryImpl
import com.mypos.store.domain.articles.model.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface ArticlesRepository {
    suspend fun readAllArticlesSuspend(): Flow<List<ArticleEntity>>
    suspend fun addArticleSuspend(articleEntity: ArticleEntity): Long
    suspend fun removeArticleSuspend(articleEntity: ArticleEntity)
    suspend fun updateArticleSuspend(articleEntity: ArticleEntity)
    suspend fun getArticleByIdSuspend(id: Int): Flow<ArticleEntity>

    fun saveToInternalStorage(bitmapImage: Bitmap, id: Int): String?
    fun readAllArticles(callback: ArticlesRepositoryImpl.RepositoryCallback<List<ArticleEntity>>)
    fun updateArticle(articleEntity: ArticleEntity)
}