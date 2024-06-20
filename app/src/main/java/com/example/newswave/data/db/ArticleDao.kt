package com.example.newswave.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newswave.data.model.Article
import kotlinx.coroutines.flow.Flow


@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllArticles(): Flow<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}


/**
 During previous lessons, we received data queries from the room database directly as live data.
 But there is problem in that approach, For MVVM architecture, it is highly recommended
 not to use live data inside the repositories.
Because live data is lifecycle aware observable data holder. We should create or emit
 LiveData from view Models and observe them from views.
 Since repositories are not connected wih life cycle, it is not recommended to use live data
 inside repositories.

 But fortunately Room library, allows us to get a data query as a coroutine flow.
 So we can later write codes to emit livedata from the flow inside the viewmodel class.

 A1
 a. fun getAllArticles(): Goal is to query saved articles data.
 Return type is a flow of list of articles. Annotated with @Query ant then sqlite query written
 to get all articles from the database.

 A2.
 NewsLocalDataSource interface: getSavedArticles() created that returns
 and NewsLocalDataSourceImpl: override getSavedArticles() where articleDao used to invoke
 getAllArticles() fun from it.


 A3.
 NewsRepository(done)
 NewsRepositoryImpl: extend the NewsRepository and implement all the members of that interface.
 Inside the getSavedNews() fun, invoke the getSavedArticles() of NewsLocalDataSource interface by
 providing  it as cp in NewsRepositoryImpl

 GetSavedNewsUseCase(already done before)


 A4.
 NewsViewModel
 NewsViewModelFactory

 A5. Dependency injection.






 */