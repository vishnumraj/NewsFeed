package com.vishnuraj.newsfeed.data.models

import com.google.gson.annotations.SerializedName
import com.vishnuraj.newsfeed.data.models.News
import java.io.Serializable

data class NewsFeedResponse(
    val title: String,
    @SerializedName("rows")
    var newsList: List<News>): Serializable