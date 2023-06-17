package com.mypos.store.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mypos.store.data.articles.dao.ArticlesDao
import com.mypos.store.data.database.util.DateConvertor
import com.mypos.store.domain.articles.model.ArticleEntity

@TypeConverters(DateConvertor::class)
@Database(entities = [ArticleEntity::class], version = 1, exportSchema = true)
abstract class StoreDatabase : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao
}