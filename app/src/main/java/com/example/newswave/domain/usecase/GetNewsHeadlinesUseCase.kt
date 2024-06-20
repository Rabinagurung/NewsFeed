package com.example.newswave.domain.usecase

import com.example.newswave.data.model.APIResponse
import com.example.newswave.data.util.Resource
import com.example.newswave.domain.repository.NewsRepository

class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository) {

    suspend fun execute(country:String, page:Int): Resource<APIResponse>{
        return newsRepository.getNewsHeadline(country, page)
    }


}

/**
 D.
 Create and execute the fun in this useCase class where r->  resource instance of type APIResponse.

 In Gradle:

 Pro Guard not shrink and optimize our code but also it obfuscate the code when we build APK our project.
 It obfuscate the code by renaming the classes and fields with different names.

 If someone try to reverse engineer the code using the apk file, we uploaded to play store,
 it will be impossible task.
 In order to enable the Pro Guard, set minifyEnables as true in the gradle app and
 isShrinkResources = true ( reduce the size of APK file)
 After sync, this will remove the unused classes and methods that are not called.

 Base URL added






 */