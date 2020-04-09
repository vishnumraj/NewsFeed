package com.vishnuraj.newsfeed.data.models

import com.vishnuraj.newsfeed.base.data.SessionEndPoint
import retrofit2.Response
import retrofit2.http.GET

class NewsFeedEndPoint : SessionEndPoint<NewsFeedAPI>() {
    override val api = NewsFeedAPI::class.java
}

interface NewsFeedAPI {
    @GET("/s/2iodh4vg0eortkl/facts.json")
    suspend fun fetchNewsFeed(): Response<NewsFeedResponse>
}