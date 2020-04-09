package com.vishnuraj.newsfeed.application

import android.app.Application
import com.vishnuraj.newsfeed.di.AppComponent
import com.vishnuraj.newsfeed.di.AppModule
import com.vishnuraj.newsfeed.di.DaggerAppComponent

/**
 * NewsFeed Application class. The DaggerComponent is created here to inject
 * dependencies to different modules
 */
class NewsFeedApplication : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = getComponent(this)
    }

    /**
     * Method build the DaggerAppComponent based on the AppComponent interface.
     * This component will be used by fragment/Activities to be available for dagger
     * dependency injection
     *
     * @param application NewsFeedApplication
     * @return AppComponent implemented AppComponent
     */
    private fun getComponent(application: NewsFeedApplication): AppComponent {
        return DaggerAppComponent.builder()
            .appModule(AppModule(application))
            .build()
    }
}