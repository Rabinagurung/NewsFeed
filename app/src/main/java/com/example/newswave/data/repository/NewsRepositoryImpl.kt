package com.example.newswave.data.repository

import android.util.Log
import com.example.newswave.data.model.APIResponse
import com.example.newswave.data.model.Article
import com.example.newswave.data.repository.dataSource.NewsLocalDataSource
import com.example.newswave.data.repository.dataSource.NewsRemoteDataSource
import com.example.newswave.data.util.Resource
import com.example.newswave.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource
): NewsRepository {
    override suspend fun getNewsHeadline(country:String, page:Int): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getTopHeadlines(country,page))
    }

    override suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Resource<APIResponse> {
        return responseToResource(newsRemoteDataSource.getSearchedNews(country,searchQuery,page))
    }

    private fun responseToResource(response:Response<APIResponse>): Resource<APIResponse>{
        if(response.isSuccessful){
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())

    }


    override suspend fun saveNews(article: Article): Long {
        return newsLocalDataSource.saveArticleToDB(article)
    }

    override suspend fun deleteNews(article: Article) {
        newsLocalDataSource.deleteArticlesFromDB(article)
    }

    override fun getSavedNews(): Flow<List<Article>> {
        return newsLocalDataSource.getSavedArticles()
    }
}


/**
 A.5
 implemented getSearchedNews and returned
 newsRemoteDataSource.getSearchedNews(country,searchQuery, page)
 -> returns APIResponse instance,
 but we need to have Resource instance with the state of the result so we are using responseToResource
 fun.
 */

/**
 C4 b .
 Here we need to add newly created newsLocalDataSource as a cp NewsRepositoryImpl
 After that, inside the override fun saveNews()-------
 write codes to invoke saveArticleToDB function of it, passing article instance as an
 argument.

 i. GetSavedNewsUseCase(check), we already have completed it.
 What did we do?
 Inside GetSavedNewsUseCase class- we provided NewsRepository instance as constructor parameter
 and executed the fun saveNews from it.

 ii. VM class(CHECK) C4 c.





 */