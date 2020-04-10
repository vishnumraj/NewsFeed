package com.vishnuraj.newsfeed.di

import com.vishnuraj.newsfeed.presentation.fragments.NewsFeedFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NewsFeedModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(target: NewsFeedFragment)
}