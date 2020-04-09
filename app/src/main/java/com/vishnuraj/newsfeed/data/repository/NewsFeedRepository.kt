package com.vishnuraj.newsfeed.data.repository

import com.vishnuraj.newsfeed.base.data.Result

interface NewsFeedRepository {

    suspend fun isNetworkAvailable(): Boolean

    suspend fun fetchNewsFeed(): Result

}