package com.example.newswave.presentation.di

import com.example.newswave.data.db.ArticleDao
import com.example.newswave.data.repository.dataSource.NewsLocalDataSource
import com.example.newswave.data.repository.dataSourceImpl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun providesNewsLocalDataSource(
        articleDao: ArticleDao
    ) : NewsLocalDataSource{
        return NewsLocalDataSourceImpl(articleDao)

    }
}