package com.example.newswave.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newswave.domain.usecase.DeleteSavedNewsUseCase
import com.example.newswave.domain.usecase.GetNewsHeadlinesUseCase
import com.example.newswave.domain.usecase.GetSavedNewsUseCase
import com.example.newswave.domain.usecase.GetSearchUseCase
import com.example.newswave.domain.usecase.SaveNewsUseCase

class NewsViewModelFactory(
    private val app: Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getSearchUseCase: GetSearchUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
):ViewModelProvider.Factory {

    //construct a view model instance and return it as T
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(
            app,
            getNewsHeadlinesUseCase,
            getSearchUseCase,
            saveNewsUseCase,
            getSavedNewsUseCase,
            deleteSavedNewsUseCase
        ) as T
    }

}

/**
A8.
 Since we added a new GetSearchUseCase as cp in NewsViewModel NewsViewModel class, we also need to add
 that GetSearchUseCase instance in NewsViewModelFactory too.

 There are two dependency injecting modules affecting from the new changes we made:
 a. Factory module
 b. UseCase module

 */

/**
 C4 c.

 Since we added a new cp(saveNewsUseCase) in VM class, we also need to add that in
 VMFactory class.

 There are two dependency injecting modules affecting from the new changes we made:

 a. Factory module



 */