package com.example.newswave.domain.usecase

import com.example.newswave.data.model.Article
import com.example.newswave.domain.repository.NewsRepository

class SaveNewsUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(article: Article): Long{
       return newsRepository.saveNews(article)
    }

}