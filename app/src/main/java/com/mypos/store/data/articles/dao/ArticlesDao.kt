package com.mypos.store.data.articles.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mypos.store.domain.articles.model.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {
    @Insert
    suspend fun addArticleSuspend(articleEntity: ArticleEntity): Long

    @Delete
    suspend fun removeArticleSuspend(articleEntity: ArticleEntity)

    @Update
    suspend fun updateArticleSuspend(articleEntity: ArticleEntity)

    @Query("SELECT * FROM articles_table ORDER BY id ASC")
    fun readAllArticlesSuspend(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM articles_table WHERE id=:id")
    fun getArticleByIdSuspend(id: Int): Flow<ArticleEntity>

    @Query("SELECT * FROM articles_table ORDER BY id ASC")
    fun readAllArticles(): List<ArticleEntity>

    @Update
    fun updateArticle(articleEntity: ArticleEntity)
}