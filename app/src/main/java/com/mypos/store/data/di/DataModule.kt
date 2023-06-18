package com.mypos.store.data.di

import android.content.Context
import androidx.room.Room
import com.mypos.store.data.articles.dao.ArticlesDao
import com.mypos.store.data.articles.repository.ArticlesRepositoryImpl
import com.mypos.store.data.cart.CartRepositoryImpl
import com.mypos.store.data.database.StoreDatabase
import com.mypos.store.domain.articles.repository.ArticlesRepository
import com.mypos.store.domain.cart.repository.CartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideWordsDatabase(@ApplicationContext appContext: Context): StoreDatabase {
        return Room.databaseBuilder(
            appContext,
            StoreDatabase::class.java,
            "store"
        ).build()
    }

    @Provides
    @Singleton
    fun provideArticlesDao(storeDatabase: StoreDatabase): ArticlesDao {
        return storeDatabase.articlesDao()
    }

    @Singleton
    @Provides
    fun provideLanguagesRepository(dao: ArticlesDao): ArticlesRepository =
        ArticlesRepositoryImpl(dao)

    @Singleton
    @Provides
    fun provideCartRepository(): CartRepository = CartRepositoryImpl()

}