package com.mypos.store.data.articles.repository

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.os.Handler
import com.mypos.store.data.articles.dao.ArticlesDao
import com.mypos.store.domain.articles.model.ArticleEntity
import com.mypos.store.domain.articles.repository.ArticlesRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.concurrent.ThreadPoolExecutor
import javax.inject.Inject
import com.mypos.store.domain.util.result.Result

class ArticlesRepositoryImpl @Inject constructor(
    private val articlesDao: ArticlesDao,
    private val context: Context,
    private val executor: ThreadPoolExecutor,
    private val handler: Handler
) :
    ArticlesRepository {

    interface RepositoryCallback<T> {
        fun onComplete(result: Result<T>?)
    }

    override fun readAllArticles(callback: RepositoryCallback<List<ArticleEntity>>) {
        executor.execute(Runnable() {
            run {
                try {
                    handler.post(Runnable {
                        val result = articlesDao.readAllArticles()
                        callback.onComplete(Result.Success(result));
                    })
                } catch (e: Exception) {
                    callback.onComplete(Result.Error(e));
                    e.printStackTrace()
                }
            }
        })
        articlesDao.readAllArticles()
    }

    override suspend fun readAllArticlesFlow(): Flow<List<ArticleEntity>> {
        return articlesDao.readAllArticlesFlow()
    }

    override suspend fun addArticle(articleEntity: ArticleEntity): Long {
        return articlesDao.addArticle(articleEntity)
    }

    override suspend fun removeArticle(articleEntity: ArticleEntity) {
        articlesDao.removeArticle(articleEntity)
    }

    override suspend fun updateArticle(articleEntity: ArticleEntity) {
        articlesDao.updateArticle(articleEntity)
    }

    override suspend fun getArticleById(id: Int): Flow<ArticleEntity> {
        return articlesDao.getArticleByIdFlow(id)
    }

    override fun saveToInternalStorage(bitmapImage: Bitmap, id: Int): String? {
        val cw = ContextWrapper(context)
        val directory = cw.getDir("articlesImages", Context.MODE_APPEND)
        val path = File(directory, "$id.jpg")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(path)
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return directory.absolutePath
    }

}