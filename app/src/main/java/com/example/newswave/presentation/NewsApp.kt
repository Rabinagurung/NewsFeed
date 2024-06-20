package com.example.newswave.presentation

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApp:Application()

/**
 H.
 Every app using the Hilt must annotate the application class with @HiltAndroidApp and this
 annotation will trigger code generation's of Hilt.

 Before using Hilt, we used to construct component in app level class.
 */