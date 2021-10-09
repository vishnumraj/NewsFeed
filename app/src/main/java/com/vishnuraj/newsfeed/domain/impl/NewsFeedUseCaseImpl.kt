/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.vishnuraj.newsfeed.domain.impl

import com.vishnuraj.newsfeed.data.models.Result
import com.vishnuraj.newsfeed.data.models.NetworkConnectionError
import com.vishnuraj.newsfeed.data.models.NewsFeedResponse
import com.vishnuraj.newsfeed.data.repository.NewsFeedRepository
import com.vishnuraj.newsfeed.domain.NewsFeedUseCase
import com.vishnuraj.newsfeed.infrastructure.NetworkManager
import javax.inject.Inject

/**
 * Use case responsible for performing the logic for fetching the News Feed. All the data
 * validation and data fetching is executed in this UseCase.
 *
 * @param repository The News feed repository
 * @param networkManager The NetworkManager class
 */
class NewsFeedUseCaseImpl @Inject constructor(private val repository: NewsFeedRepository,
                                              private val networkManager: NetworkManager) : NewsFeedUseCase {

    override suspend fun fetchNewsFeed(): Result {
        return if (networkManager.isNetworkAvailable()) {
            when (val result = repository.fetchNewsFeed()) {
                is Result.Success<*> -> {
                    val newsFeedResponse = result.response as NewsFeedResponse

                    // filter out the news item if all the new parameters are null
                    newsFeedResponse.newsList = newsFeedResponse
                        .newsList.filter {
                            it.title != null
                                    || it.description != null
                                    || it.imageUrl != null
                        }
                    Result.Success(newsFeedResponse)
                }
                else -> {
                    result
                }
            }
        } else {
            Result.Failure(NetworkConnectionError())
        }
    }
}