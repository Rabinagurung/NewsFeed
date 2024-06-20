package com.example.newswave.presentation.di

import com.example.newswave.BuildConfig
import com.example.newswave.data.api.NewsAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun newsAPIService(retrofit: Retrofit):NewsAPIService{
        return retrofit.create(NewsAPIService::class.java)
    }
}


/***

 I.i
NetworkModule:
 In pure dagger, we created component interfaces and state this modules there.
 But with dagger hilt, we already have in-built set up components.
 All we need to do is annotate the Hilt modules with @Install annotation to tell Hilt in which
    Android class,each module will be used in or installed in.

b.
Types of component in Hilt:
     If we want to use dependencies provided by the modules in the entire application then
    SingletonComponent is used.
     Or to use the dependencies provided by the modules in the activity then
     ActivityComponent is used
     or to use the dependencies provided by the modules in the fragment then
     FragmentComponent is used.

In this module we will create two funs:
    i. fun provideRetrofit(): Retrofit
    PROVIDES  a retrofit instance

    ii. fun newsAPIService(retrofit:Retrofit): NewsAPIService
    PROVIDES a NewsAPIService instance.
    This fun takes the Retrofit dependency as parameter which was just created above.






 */