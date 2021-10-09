package com.vishnuraj.newsfeed.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * NewsFeed Application class. The DaggerComponent is created here to inject
 * dependencies to different modules
 */
@HiltAndroidApp
class NewsFeedApplication : Application()