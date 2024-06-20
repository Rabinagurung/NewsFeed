package com.example.newswave.presentation.di

import com.example.newswave.data.api.NewsAPIService
import com.example.newswave.data.repository.NewsRepositoryImpl
import com.example.newswave.data.repository.dataSource.NewsRemoteDataSource
import com.example.newswave.data.repository.dataSourceImpl.NewsRemoteDataSourceImpl
import com.example.newswave.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(
        newsAPIService: NewsAPIService
    ): NewsRemoteDataSource{
        return NewsRemoteDataSourceImpl(newsAPIService)
    }

}


/**

 I.ii

 fun provideNewsRemoteDataSource(): provide instance of NewsRemoteDataSource interface

    This fun should take an instance of NewsAPIService as a parameter because
    NewsRemoteDataSourceImpl(check) class was created implementing NewsRemoteDataSource interface,
    this impl class requires instance of NewsAPIService as cp.
    Inside this new function, we are planning to construct a NewsRemoteDataSourceImpl, so
    we need to provide NewsAPIService dependency as a parameter to this function.
    That's how we think before returning a function.

 */
