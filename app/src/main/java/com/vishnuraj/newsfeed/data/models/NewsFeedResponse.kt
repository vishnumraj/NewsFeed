package com.vishnuraj.newsfeed.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Class representing the NewsFeed data from the Server.
 *
 * @param title Title name
 * @param newsList The list representing the NewsFeed.
 */
data class NewsFeedResponse(
    val title: String,
    @SerializedName("rows")
    var newsList: List<News>
) : Serializable