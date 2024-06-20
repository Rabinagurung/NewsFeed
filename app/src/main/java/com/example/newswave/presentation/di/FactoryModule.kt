package com.example.newswave.presentation.di

import android.app.Application
import com.example.newswave.domain.usecase.DeleteSavedNewsUseCase
import com.example.newswave.domain.usecase.GetNewsHeadlinesUseCase
import com.example.newswave.domain.usecase.GetSavedNewsUseCase
import com.example.newswave.domain.usecase.GetSearchUseCase
import com.example.newswave.domain.usecase.SaveNewsUseCase
import com.example.newswave.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun providesNewsViewModelFactory(
        application: Application,
        getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
        getSearchUseCase: GetSearchUseCase,
        saveNewsUseCase: SaveNewsUseCase,
        getSavedNewsUseCase: GetSavedNewsUseCase,
        getDeleteSavedNewsUseCase: DeleteSavedNewsUseCase
    ):NewsViewModelFactory{
        return NewsViewModelFactory(
            application,
            getNewsHeadlinesUseCase,
            getSearchUseCase,
            saveNewsUseCase,
            getSavedNewsUseCase,
            getDeleteSavedNewsUseCase
        )

    }
}

/***
Module class created to provide instance of NewsViewModelFactory(check) has two external
dependencies: Application and getNewsHeadlinesUseCase. So in provide fun we add parameters as
instance of Application and instance of GetNewsHeadlinesUseCase and  return type is
NewsViewModelFactory instance.
Then construct and return the dependency.
*/


/***
 C4d Since we added reference to SaveNewsUseCase in VM, VMFactory as a new constructor parameter.

 This Module class is responsible for creating instance of VMFactory.
 We also need to add the reference to SaveNewsUseCase as parameter here.
 Then create object of VMFactory adding the new parameter and return it.
*/




