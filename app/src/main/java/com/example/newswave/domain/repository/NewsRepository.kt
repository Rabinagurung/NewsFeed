package com.example.newswave.domain.repository

import com.example.newswave.data.model.APIResponse
import com.example.newswave.data.model.Article
import com.example.newswave.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getNewsHeadline(country:String, page:Int): Resource<APIResponse>
    suspend fun getSearchedNews(country: String, searchQuery: String, page: Int): Resource<APIResponse>
    suspend fun saveNews(article: Article): Long
    suspend fun deleteNews(article: Article)
    fun getSavedNews(): Flow<List<Article>>

}

/**

A4.
 suspend fun getSearchedNews() already created before but we did not added country and page
 parameters, so we added.

 */