package com.vishnuraj.newsfeed

import com.google.gson.Gson
import com.vishnuraj.newsfeed.data.models.Result
import com.vishnuraj.newsfeed.data.models.NewsFeedError
import com.vishnuraj.newsfeed.data.models.NewsFeedResponse
import retrofit2.Response
import java.io.File


object MockRepositoryResponse {

    fun getRetrofitResponse(): Response<NewsFeedResponse> {
        val classLoader = javaClass.classLoader
        val resource = classLoader.getResource("NewsFeed.json")
        val file = File(resource!!.path)
        val jsonString = file.readText(Charsets.UTF_8)
        return Response.success(Gson().fromJson(jsonString,NewsFeedResponse::class.java))
    }

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