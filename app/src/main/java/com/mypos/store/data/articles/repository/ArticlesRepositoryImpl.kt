package com.mypos.store.data.articles.repository

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import com.mypos.store.data.articles.dao.ArticlesDao
import com.mypos.store.domain.articles.model.ArticleEntity
import com.mypos.store.domain.articles.repository.ArticlesRepository
import kotlinx.coroutines.flow.Flow
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject


class ArticlesRepositoryImpl @Inject constructor(
    private val articlesDao: ArticlesDao,
    private val context: Context
) :
    ArticlesRepository {

    override suspend fun readAllArticles(): Flow<List<ArticleEntity>> {
        return articlesDao.readAllArticles()
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
        return articlesDao.getArticleById(id)
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

//    private fun loadImageFromStorage(path: String) {
//        try {
//            val f = File(path, "profile.jpg")
//            val b = BitmapFactory.decodeStream(FileInputStream(f))
//            val img = findViewById(R.id.imgPicker) as ImageView
//            img.setImageBitmap(b)
//        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
//        }
//    }

}