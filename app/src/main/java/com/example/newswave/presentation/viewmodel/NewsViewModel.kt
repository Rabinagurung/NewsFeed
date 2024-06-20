package com.example.newswave.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.newswave.data.model.APIResponse
import com.example.newswave.data.model.Article
import com.example.newswave.data.util.Resource
import com.example.newswave.domain.usecase.DeleteSavedNewsUseCase
import com.example.newswave.domain.usecase.GetNewsHeadlinesUseCase
import com.example.newswave.domain.usecase.GetSavedNewsUseCase
import com.example.newswave.domain.usecase.GetSearchUseCase
import com.example.newswave.domain.usecase.SaveNewsUseCase
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    private val app:Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getSearchUseCase: GetSearchUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
): AndroidViewModel(app) {

    val newsHeadLines:MutableLiveData<Resource<APIResponse>> = MutableLiveData()



    fun getNewsHeadline(country:String, page:Int) = viewModelScope.launch(Dispatchers.IO) {
        newsHeadLines.postValue(Resource.Loading())
        try{
            if(isNetworkAvailable(app)){
                val apiResult = getNewsHeadlinesUseCase.execute(country, page)
                newsHeadLines.postValue(apiResult)
            }else {
                newsHeadLines.postValue(Resource.Error("Internet is not available"))
            }

        } catch (e:Exception){
            newsHeadLines.postValue(Resource.Error(e.message.toString()))
        }

    }

    private fun isNetworkAvailable(context: Context?):Boolean{
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false

    }


    //search
    val searchedNews: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun searchNews(
        country: String,
        searchQuery:String,
        page:Int
    ) = viewModelScope.launch {
        searchedNews.postValue(Resource.Loading())
        try {
            if(isNetworkAvailable(app)){
                val response = getSearchUseCase.execute(country,searchQuery,page)
                searchedNews.postValue(response)

            }else {
                searchedNews.postValue(Resource.Error("No internet connection"))
            }

        } catch (e:Exception){
            searchedNews.postValue(Resource.Error(e.message.toString()))
        }
    }

    private val _saveArticleStatus = MutableLiveData<String>()
    val saveArticleStatus: LiveData<String> get() = _saveArticleStatus
    //local data
    fun saveArticle(article: Article) = viewModelScope.launch {
        try{
            val rowId = saveNewsUseCase.execute(article)
            if(rowId > 0){
                _saveArticleStatus.value = "Saved successfully"

            }else {
                _saveArticleStatus.value = "Failed to save"
            }

        }catch(e: Exception){
            _saveArticleStatus.value = "An error occurred: ${e.message}"
        }

    }


    //get saved articles
   fun getSavedNews() =  liveData{
        getSavedNewsUseCase.execute().collect{
            emit(it)
        }
    }


    //delete Articles
    fun deleteArticles(article: Article) = viewModelScope.launch {
        deleteSavedNewsUseCase.execute(article)

    }
}
/**
 A4.
    Provide GetSavedNewsUseCase interface as cp. Then we will write the codes to get the data query
    as a flow from the this UseCase class and convert it to live data.
    To do that we will use coroutine live data builder, then we going to execute fun of
    getSavedNewsUseCase then collect then finally emit it as live data

 */

/*** old
 A7.
 GetSearchUseCase instance will be added as a cp to this VM like GetNewsHeadlinesUseCase.

 Codes written to get the search results from GetSearchUseCase class.
 i. Define the mutable live data for the searched list
 ii. fun searchNews created, network operations must be done in background thread co-routines used.
 iii. In fun-> first loading state and internet availability is checked before we call api.
 iv. If internet is available then we can write codes to get the search results using the
    getSearchUseCase instance.

 */


/** old
 C4 c.
 i. Create a fun to save an article instance and coroutines is launched using viewModelScope.
 ii. Inside the background thread, invoke execute fun of the SaveNewsUseCase.
    To do that first, we need SaveNewsUseCase. So, reference to SaveNewsUseCase is added
    as cp.
    Then execute saveNews fun of SaveNewsUseCase.

 iii. Since we added a new cp(saveNewsUseCase) in VM class, we also need to add that in
 VMFactory class.












 */