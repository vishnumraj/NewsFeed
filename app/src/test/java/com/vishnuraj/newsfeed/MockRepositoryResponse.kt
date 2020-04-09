package com.vishnuraj.newsfeed

import com.google.gson.Gson
import com.vishnuraj.newsfeed.base.data.Result
import com.vishnuraj.newsfeed.data.models.NewsFeedError
import com.vishnuraj.newsfeed.data.models.NewsFeedResponse
import java.io.File


object MockRepositoryResponse {

    fun getResponseData(): Result {
        val classLoader = javaClass.classLoader
        val resource = classLoader.getResource("NewsFeed.json")
        val file = File(resource!!.path)
        val jsonString = file.readText(Charsets.UTF_8)
        return Result.Success(Gson().fromJson(jsonString,NewsFeedResponse::class.java))
    }

    fun getErrorData(): Result {
        return Result.Failure(NewsFeedError("Server Error"))
    }
}