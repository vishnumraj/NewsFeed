package com.vishnuraj.newsfeed.di

import android.content.Context
import com.vishnuraj.newsfeed.data.repository.NewsFeedRepository
import com.vishnuraj.newsfeed.data.repository.impl.NewsFeedRepositoryImpl
import com.vishnuraj.newsfeed.domain.NewsFeedUseCase
import com.vishnuraj.newsfeed.domain.impl.NewsFeedUseCaseImpl
import com.vishnuraj.newsfeed.infrastructure.NetworkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.create

/**
 * Dagger Module class for News Feed
 */
@Module
@InstallIn(ViewModelComponent::class)
class NewsFeedModule {

    @Provides
    fun providesNewsFeedRepository(retrofit: Retrofit): NewsFeedRepository =
        NewsFeedRepositoryImpl(retrofit.create())

    @Provides
    fun providesNewsFeedUseCase(repository: NewsFeedRepository, networkManager: NetworkManager): NewsFeedUseCase =
        NewsFeedUseCaseImpl(repository, networkManager)
}