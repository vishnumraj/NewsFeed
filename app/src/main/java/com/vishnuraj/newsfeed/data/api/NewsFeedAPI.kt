package com.vishnuraj.newsfeed.data.api

import com.vishnuraj.newsfeed.data.models.NewsFeedResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Interface representing the http method for getting the NewsFeedResponse
 * from the server.
 */
interface NewsFeedAPI {

    /**
     * Method representing http GET request to fetch the NewsFeedResponse
     * from the server.
     */
    @GET("/s/2iodh4vg0eortkl/facts.json")
    suspend fun fetchNewsFeed(): Response<NewsFeedResponse>
}