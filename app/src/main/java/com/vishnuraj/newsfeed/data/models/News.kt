package com.vishnuraj.newsfeed.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Class Representing the News item in the NewsFeed
 *
 * @param title The News title
 * @param description The news description
 * @param imageUrl The url to the Image
 */
data class News(
    val title: String?,
    val description: String?,
    @SerializedName("imageHref")
    val imageUrl: String?
) : Serializable