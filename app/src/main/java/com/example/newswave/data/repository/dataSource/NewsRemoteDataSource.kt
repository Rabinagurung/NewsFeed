package com.example.newswave.data.repository.dataSource

import com.example.newswave.data.model.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource {
 suspend fun getTopHeadlines(country:String, page:Int):Response<APIResponse>
 suspend fun getSearchedNews(country: String, searchQuery:String, page: Int): Response<APIResponse>

}

/**
 A.2

 getSearchedNews fun created in the NewsRemoteDataSource interface with three cp: country,
 searchQuery and page.


 */

