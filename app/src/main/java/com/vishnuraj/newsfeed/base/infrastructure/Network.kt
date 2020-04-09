package com.vishnuraj.newsfeed.base.infrastructure

import com.vishnuraj.newsfeed.base.data.SessionEndPoint
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Network class for initiating the Network Request. The method build the Retrofit based on
 * the data from the SessionEndPoint.
 */
object Network {

    /**
     * Method to initiate an http connection. Method uses Retrofit builder
     * and SessionEndPoint Object to build the request.
     *
     * @param endPoint The SessionEndPoint.
     */
    fun <T> connect(endPoint: SessionEndPoint<T>): T {
        return Retrofit.Builder()
            .baseUrl(endPoint.baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(endPoint.api)
    }
}