package com.vishnuraj.newsfeed.domain

import com.vishnuraj.newsfeed.data.models.Result

interface NewsFeedUseCase {
    suspend fun fetchNewsFeed() : Result
}