package com.example.newswave.presentation.di

import com.example.newswave.data.repository.NewsRepositoryImpl
import com.example.newswave.data.repository.dataSource.NewsLocalDataSource
import com.example.newswave.data.repository.dataSource.NewsRemoteDataSource
import com.example.newswave.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        newsLocalDataSource: NewsLocalDataSource
    ): NewsRepository{
        return NewsRepositoryImpl(newsRemoteDataSource, newsLocalDataSource)
    }

}

/**
 I. iii.

 A provider function to provide a NewsRepository instance.

 */


/**
 C4 d.
 NewsRepository interface is implemented in NewsRepositoryImpl where we added new constructor
 parameter: newsLocalDataSource.
 So we also need to update here and add newsLocalDataSource instance as parameter in provides fun.
 Then pass it while creating and returning object of NewsRepositoryImpl

 */