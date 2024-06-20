package com.example.newswave.data.repository.dataSource

import com.example.newswave.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    suspend fun saveArticleToDB(article: Article): Long
    fun getSavedArticles():Flow<List<Article>>
    suspend fun deleteArticlesFromDB(article: Article)
}

/**
 A2.
 A function for getting saved data. Returns Flow<List<Article>>


 */

/** old
 C4 a.
 Define a fun for saving data.
 Create implementation class for NewsLocalDataSource interface(part of C4 a )

 */