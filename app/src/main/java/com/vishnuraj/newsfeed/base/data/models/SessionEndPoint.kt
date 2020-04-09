package com.vishnuraj.newsfeed.base.data.models

/**
 * abstract class acting as a base for Network Service request. The class
 * has two parameters baseURL and api representing the Retrofit interface
 * class
 */
abstract class SessionEndPoint<T> {

    /**
     * base URL for the Network request
     */
    val baseURL: String = "https://dl.dropboxusercontent.com"

    /**
     * abstract api representing the retrofit class
     */
    abstract val api: Class<out T>

}