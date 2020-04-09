package com.vishnuraj.newsfeed.data.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class News(
    val title: String?,
    val description: String?,
    @SerializedName("imageHref")
    val imageUrl: String?
) : Serializable