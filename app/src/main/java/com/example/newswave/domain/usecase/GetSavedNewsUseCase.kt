package com.example.newswave.domain.usecase

import com.example.newswave.data.model.Article
import com.example.newswave.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {
    fun execute(): Flow<List<Article>> {
        return newsRepository.getSavedNews()
    }

}


