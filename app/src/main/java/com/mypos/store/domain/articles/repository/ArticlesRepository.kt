package com.mypos.store.domain.articles.repository

import com.mypos.store.domain.articles.model.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface ArticlesRepository {
    suspend fun readAllArticles(): Flow<List<ArticleEntity>>
    suspend fun addArticle(articleEntity: ArticleEntity)
    suspend fun removeArticle(articleEntity: ArticleEntity)
    suspend fun updateArticle(articleEntity: ArticleEntity)
    suspend fun getArticleById(id: Int): Flow<ArticleEntity>
}