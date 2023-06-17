package com.mypos.store.data.articles.repository

import com.mypos.store.data.articles.dao.ArticlesDao
import com.mypos.store.domain.articles.model.ArticleEntity
import com.mypos.store.domain.articles.repository.ArticlesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(private val articlesDao: ArticlesDao) :
    ArticlesRepository {

    override suspend fun readAllArticles(): Flow<List<ArticleEntity>> {
        return articlesDao.readAllArticles()
    }

    override suspend fun addArticle(articleEntity: ArticleEntity) {
        articlesDao.addArticle(articleEntity)
    }

    override suspend fun removeArticle(articleEntity: ArticleEntity) {
        articlesDao.removeArticle(articleEntity)
    }

    override suspend fun updateArticle(articleEntity: ArticleEntity) {
        articlesDao.updateArticle(articleEntity)
    }

    override suspend fun getArticleById(id: Int): Flow<ArticleEntity> {
        return articlesDao.getArticleById(id)
    }

}