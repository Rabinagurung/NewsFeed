package com.example.newswave.data.api

import com.example.newswave.BuildConfig
import com.example.newswave.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")
        country:String,
        @Query("page")
        page:Int,
        @Query("apiKey")
        apiKey:String = BuildConfig.API_KEY
    ): Response<APIResponse>


    @GET("v2/top-headlines")
    suspend fun getSearchedTopHeadlines(
        @Query("country")
        country: String,
        @Query("q")
        searchQuery:String,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY
    ):Response<APIResponse>
}

/**
 *
A.1
 For Search Functionality: We are going to start from the bottom of the architecture diagram.
    a. APIServiceInterface
    b. Update the NewsRemoteDataSource interface and NewsRemoteDataSourceImpl class.
    c. Change the NewsRepository interface, NewsRepositoryImpl class
    d. Update the use case class
    e. Update ViewModel, ViewModelFactory related dependency injection modules and NewsFragment.

 a. APIServiceInterface:
    If you search in newsapi.org: q = request parameter for search.
    That means if we include search query parameter to the request, server will send the list of
    search results.
    All we need to do this include search parameter q in this fun.

 b. NewsRemoteDataSource interface:
    Add a new function in NewsRemoteDataSource interface for searched data.


 */