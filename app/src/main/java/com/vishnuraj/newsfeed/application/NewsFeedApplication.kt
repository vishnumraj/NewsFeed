package com.vishnuraj.newsfeed.application

import android.app.Application
import com.vishnuraj.newsfeed.di.AppComponent
import com.vishnuraj.newsfeed.di.AppModule
import com.vishnuraj.newsfeed.di.DaggerAppComponent

class NewsFeedApplication : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = getComponent(this)
    }

    private fun getComponent(application: NewsFeedApplication): AppComponent {
        return DaggerAppComponent.builder()
            .appModule(AppModule(application))
            .build()
    }
}