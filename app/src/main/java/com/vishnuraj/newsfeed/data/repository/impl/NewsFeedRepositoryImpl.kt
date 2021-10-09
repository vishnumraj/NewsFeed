package com.vishnuraj.newsfeed.data.repository.impl

import android.content.Context
import android.net.ConnectivityManager
import com.vishnuraj.newsfeed.data.api.NewsFeedAPI
import com.vishnuraj.newsfeed.data.models.NewsFeedError
import com.vishnuraj.newsfeed.data.models.Result
import com.vishnuraj.newsfeed.data.repository.NewsFeedRepository
import javax.inject.Inject

/**
 * Class Implementing the NewsFeedRepository. Class provide implementation for
 * network available check and fetching the News Feed.
 */
class NewsFeedRepositoryImpl @Inject constructor(private val api: NewsFeedAPI) :
    NewsFeedRepository {

    override suspend fun fetchNewsFeed(): Result {
        val response = api.fetchNewsFeed()
        return if (response.isSuccessful) Result.Success(response.body())
        else Result.Failure(NewsFeedError(response.errorBody().toString()))
    }

}