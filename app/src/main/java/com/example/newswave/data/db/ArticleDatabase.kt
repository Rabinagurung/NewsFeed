package com.example.newswave.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newswave.data.model.Article

@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ArticleDatabase: RoomDatabase() {
    abstract fun getArticlesDao():ArticleDao

}

/**
 C4.
 Created Room database class called ArticleDatabase.
 This should be an abstract class and extend Room database class, annotate as Room database class
 with @Database  annotation and add all the related entity class.
 i. We only have one entity class here Article, then we add version and exportSchema false.
 ii. We created type converter class called Converters, we need to add it here with type
 converters annotations.

 iii. Finally define an abstract getter function for DAO interfaces.

 We will implement SaveNewsDataUseCase.
 Start from the bottom.
 a. Creating a local data source interface and its impl class.
 ---------C4a. data/repository/dataSource/NewsLocalDataSource
 ---------C4a. data/repository/dataSource/NewsLocalDataSourceImpl

 b. Lets check the repository interface.
 ---------C4b. repository/NewsRepository(check)
 We already have defined saveNews fun.
 ----------C4b.  data/repository/NewsRepositoryImpl

 c. VM class NewsViewModel

 ----------C4c.  presentation/viewModel/NewsViewModel
 ----------C4c.  presentation/viewModel/NewsViewModelFactory

 d. Changes we need to do in Dependency Injection modules:

 ----------C4d.  presentation/di/FactoryModule
 ----------C4d.  presentation/di/RepositoryModule
 ----------C4d.  presentation/di/UseCaseModule

 e. Create three modules:
 To provide the database instances.
 ----------C4e.  presentation/di/DatabaseModule

 To provide dao interface instance
 ----------C4e.  presentation/di/DaoModule

 To provide instance of LocalDataSource
 ----------C4e.  presentation/di/LocalDataModule

 D. Errors
 a. NewsAdapter: source is nullable so ? added.
 b. InfoFragment: TO load loadUrl fun,
 we must have a real String value for url, it cannot be null.
 As url is nullable, so we must check if it is null or not if(url != null)



 E. Floating button added in info fragment xml file.
 Setting onClick for that floating button: We need to execute the saveNews fun of NewsVM.
 So, newsViewModel instance is already there in MA, where it is initialized with
 ModelViewProvider and factory is also added.

 Our concept is if we want to send the data from VM to fragment, then VM should be initialized
 in MA then from there fragments can use functions of VM.

 Here:  we just added the newsVM instance in InfoFragment using newModel instance present
 in VM where we first initialized it.


 */