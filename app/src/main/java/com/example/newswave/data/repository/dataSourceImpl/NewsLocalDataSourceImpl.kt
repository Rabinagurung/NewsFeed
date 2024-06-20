package com.example.newswave.data.repository.dataSourceImpl

import com.example.newswave.data.db.ArticleDao
import com.example.newswave.data.model.Article
import com.example.newswave.data.repository.dataSource.NewsLocalDataSource
import kotlinx.coroutines.flow.Flow

class NewsLocalDataSourceImpl(
    private val articleDao: ArticleDao
):NewsLocalDataSource {
    override suspend fun saveArticleToDB(article: Article): Long {
       return articleDao.insert(article)
    }

    override fun getSavedArticles(): Flow<List<Article>> {
        return articleDao.getAllArticles()
    }

    override suspend fun deleteArticlesFromDB(article: Article) {
        articleDao.deleteArticle(article)
    }
}

/**
 C4 a.
 Implemented its interface.
 In order to save article data to database we need DAO interfaces(check it has fun insert
 that saves the article instance to Articles table). So DAO interface is
 provided as cp.
 Inside the saveArticlesToDB fun, invoke the articleDo insert fun.

 Lets check the repository interface.  C4 b.



 */