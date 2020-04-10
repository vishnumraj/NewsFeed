package com.vishnuraj.newsfeed.data.models

import com.vishnuraj.newsfeed.base.data.models.SessionEndPoint
import retrofit2.Response
import retrofit2.http.GET

/**
 * Class representing the SessionEndPoint for the Network layer. This
 * class basically return the interface representing http request for
 * the retrofit.
 */
class NewsFeedEndPoint : SessionEndPoint<NewsFeedAPI>() {
    override val api = NewsFeedAPI::class.java
}

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