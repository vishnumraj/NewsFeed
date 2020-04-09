package com.vishnuraj.newsfeed.di

import android.content.Context
import com.vishnuraj.newsfeed.data.repository.NewsFeedRepository
import com.vishnuraj.newsfeed.data.repository.impl.NewsFeedRepositoryImpl
import com.vishnuraj.newsfeed.domain.NewsFeedUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class NewsFeedModule {

    @Provides
    @Inject
    @Singleton
    fun providesNewsFeedRepository(context: Context): NewsFeedRepository =
        NewsFeedRepositoryImpl(context)

    @Provides
    @Inject
    @Singleton
    fun providesNewsFeedUseCase(repository: NewsFeedRepository): NewsFeedUseCase =
        NewsFeedUseCase(repository)
}