package com.example.newswave.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.newswave.data.db.ArticleDao
import com.example.newswave.data.db.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun providesNewsDatabase(app:Application): ArticleDatabase{
        return Room.databaseBuilder(app, ArticleDatabase::class.java, "news_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesNewsDao(articleDatabase: ArticleDatabase): ArticleDao{
        return articleDatabase.getArticlesDao()
    }




}

/**
 C4 e.

 This module provides database instance and dao dependency.

 i. fun providesNewsDatabase- pass the application class as parameter

 and create and return Room database using fun databaseBuilder that requires
 app context, database class we are creating and lastly name of database.
 Then fallbackToDestructiveMigrationFrom() will allow Room to destructively replace
 database tables if migration(that would migrate database schemas to the latest schema version
 are not found).
 Finally build

 Since this database name is constant value we could define it as constant in a separate
 constants class and provide the name of the constant here.

 But in this project we only have one constant so we are not going to create a separate class
 for constants.

 ii. fun providesNewsDao - for DAO dependency.







 */