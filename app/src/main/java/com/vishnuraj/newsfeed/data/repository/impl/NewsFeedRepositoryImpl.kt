package com.vishnuraj.newsfeed.data.repository.impl

import android.content.Context
import android.net.ConnectivityManager
import com.vishnuraj.newsfeed.base.data.Result
import com.vishnuraj.newsfeed.base.infrastructure.Network
import com.vishnuraj.newsfeed.data.models.NewsFeedEndPoint
import com.vishnuraj.newsfeed.data.repository.NewsFeedRepository
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(private val context: Context): NewsFeedRepository {

    override suspend fun isNetworkAvailable(): Boolean {
        val service = Context.CONNECTIVITY_SERVICE
        val manager = context.getSystemService(service) as ConnectivityManager?
        val network = manager?.activeNetworkInfo
        return (network != null)
    }

    override suspend fun fetchNewsFeed(): Result {
        val response = Network.connect(NewsFeedEndPoint()).fetchNewsFeed()
        return if (response.isSuccessful) Result.Success(response.body())
        else Result.Failure(Error(response.errorBody().toString()))
    }

}