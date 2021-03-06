package com.vishnuraj.newsfeed.domain

import com.vishnuraj.newsfeed.base.data.models.Result
import com.vishnuraj.newsfeed.base.domain.UseCase
import com.vishnuraj.newsfeed.data.models.NetworkConnectionError
import com.vishnuraj.newsfeed.data.models.NewsFeedResponse
import com.vishnuraj.newsfeed.data.repository.NewsFeedRepository
import javax.inject.Inject

/**
 * Use case responsible for performing the logic for fetching the News Feed. All the data
 * validation and data fetching is executed in this UseCase.
 *
 * @param repository The News feed repository
 */
class NewsFeedUseCase @Inject constructor(private val repository: NewsFeedRepository) :
    UseCase<Void, Result> {

    override suspend fun execute(params: Void?): Result {
        return if (repository.isNetworkAvailable()) {
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