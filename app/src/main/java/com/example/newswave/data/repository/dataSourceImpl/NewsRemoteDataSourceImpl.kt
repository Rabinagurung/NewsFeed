package com.example.newswave.data.repository.dataSourceImpl

import com.example.newswave.data.api.NewsAPIService
import com.example.newswave.data.model.APIResponse
import com.example.newswave.data.repository.dataSource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsAPIService: NewsAPIService,
): NewsRemoteDataSource {
    override suspend fun getTopHeadlines(country:String, page:Int): Response<APIResponse> {
        return newsAPIService.getTopHeadlines(country, page)
    }

    override suspend fun getSearchedNews(
        country: String,
        searchQuery: String,
        page: Int
    ): Response<APIResponse> {
        return newsAPIService.getSearchedTopHeadlines(country,searchQuery,page)
    }
}

/**
 *
 A.3
    implemented getSearchedNews() as we implemented NewsRemoteDataSource interface
    which has 3 params: country, page and apiKey(already provided).
    We need to provide the country(String) and page(Int) from this new function.
    So they are added as cp.
    We also require instance of newsAPIService, to be able to call fun getTopHeadlines() of the
    NewsAPIService passing the country and page id as arguments and api_key is already provided.



 */

