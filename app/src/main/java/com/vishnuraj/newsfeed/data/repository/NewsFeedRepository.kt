package com.vishnuraj.newsfeed.data.repository

import com.vishnuraj.newsfeed.data.models.Result

/**
 * Interface representing the NewsFeedRepository. The repository
 * server two main purpose. Expose an API to check if Network is
 * available and Fetch the NewsFeed data.
 */
interface NewsFeedRepository {


    /**
     * Method to fetch the NewsFeed data from the server
     *
     * @return Result object
     */
    suspend fun fetchNewsFeed(): Result

}