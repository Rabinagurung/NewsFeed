package com.example.newswave.presentation.di

import com.example.newswave.domain.repository.NewsRepository
import com.example.newswave.domain.usecase.DeleteSavedNewsUseCase
import com.example.newswave.domain.usecase.GetNewsHeadlinesUseCase
import com.example.newswave.domain.usecase.GetSavedNewsUseCase
import com.example.newswave.domain.usecase.GetSearchUseCase
import com.example.newswave.domain.usecase.SaveNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun providesNewsHeadlineUseCase(
        newsRepository: NewsRepository
    ): GetNewsHeadlinesUseCase{

        return GetNewsHeadlinesUseCase(newsRepository)

    }

    @Singleton
    @Provides
    fun providesSearchNewsUseCase(
        newsRepository: NewsRepository
    ): GetSearchUseCase{
        return GetSearchUseCase(newsRepository)

    }

    @Singleton
    @Provides
    fun providesSaveNewsUseCase(
        newsRepository: NewsRepository
    ): SaveNewsUseCase{
        return SaveNewsUseCase(newsRepository)
    }


    @Singleton
    @Provides
    fun providesGetSavedNewsUseCase(
        newsRepository: NewsRepository
    ): GetSavedNewsUseCase{
        return GetSavedNewsUseCase(newsRepository)
    }

    @Singleton
    @Provides
    fun providesDeleteSavedNewsUseCase(
        newsRepository: NewsRepository
    ):DeleteSavedNewsUseCase{
        return DeleteSavedNewsUseCase(newsRepository)
    }
}

//old
/***
A9.

I.
useCaseModule created to provide an instance of GetNewsHeadlinesUseCase(check) as dependency
where NewsRepository is cp. So in provide fun we add parameters as instance of newsRepository
and return type is GetNewsHeadlinesUseCase(WE WANT OBJECT OF THI USE CASE)

Then construct and return the dependency.

II.
Creating instance of GetSearchUseCase: detail
GetSearchUseCase class(check) has NewsRepository instance as dependency in cp.
So we create fun providesSearchNewsUseCase annotated with @Provides  annotation, so that hilt
knows that this fun is special and hilt needs to provide the instance of GetSearchUseCase
so we created and returned the instance of GetSearchUseCase.

 */

//new

/**
 C4 d. III.
 Creating instance of SaveNewsUseCase: detail
 SaveNewsUseCase class(check) has NewsRepository instance as dependency in cp.
 So we create fun providesSaveNewsUseCase annotated with @Provides annotation so that hilt knows
 that this fun is special and hilt needs to provide the instance of SaveNewsUseCase
 so we created and returned the object of SaveNewsUseCase .
 Also, annotated fun with @Singleton annotation.
 */



