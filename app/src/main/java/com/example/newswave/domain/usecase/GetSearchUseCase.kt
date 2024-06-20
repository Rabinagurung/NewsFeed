package com.example.newswave.domain.usecase

import androidx.room.Query
import com.example.newswave.data.model.APIResponse
import com.example.newswave.data.util.Resource
import com.example.newswave.domain.repository.NewsRepository

class GetSearchUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(country:String, searchQuery: String, page:Int):Resource<APIResponse>{
        return newsRepository.getSearchedNews(country,searchQuery,page)
    }

}


/**
 A6. Since we have added newsRepository instance here as cp, we need to add country and page as cp
 in execution function.
 and passed them to the newsRepository.getSearchedNews()
 */