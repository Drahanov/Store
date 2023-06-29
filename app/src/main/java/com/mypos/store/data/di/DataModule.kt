package com.mypos.store.data.di

import android.content.Context
import android.os.Handler
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
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
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
        ).allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideArticlesDao(storeDatabase: StoreDatabase): ArticlesDao {
        return storeDatabase.articlesDao()
    }

    @Singleton
    @Provides
    fun provideLanguagesRepository(
        dao: ArticlesDao,
        @ApplicationContext context: Context,
        threadPoolExecutor: ThreadPoolExecutor,
        handler: Handler
    ): ArticlesRepository =
        ArticlesRepositoryImpl(dao, context, threadPoolExecutor, handler)

    @Singleton
    @Provides
    fun provideCartRepository(): CartRepository = CartRepositoryImpl()

    private val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()
    private val workQueue: BlockingQueue<Runnable> = LinkedBlockingQueue()
    private const val KEEP_ALIVE_TIME = 1
    private val KEEP_ALIVE_TIME_UNIT: TimeUnit = TimeUnit.SECONDS

    @Singleton
    @Provides
    fun provideResultHandler(): Handler = Handler()

    @Singleton
    @Provides
    fun provideExecutor(): ThreadPoolExecutor = ThreadPoolExecutor(
        NUMBER_OF_CORES,
        NUMBER_OF_CORES,
        KEEP_ALIVE_TIME.toLong(),
        KEEP_ALIVE_TIME_UNIT,
        workQueue
    )
}