package com.vishnuraj.newsfeed.domain

import com.vishnuraj.newsfeed.MockRepositoryResponse
import com.vishnuraj.newsfeed.data.api.NewsFeedAPI
import com.vishnuraj.newsfeed.data.models.NetworkConnectionError
import com.vishnuraj.newsfeed.data.models.NewsFeedError
import com.vishnuraj.newsfeed.data.models.NewsFeedResponse
import com.vishnuraj.newsfeed.data.models.Result
import com.vishnuraj.newsfeed.data.repository.NewsFeedRepository
import com.vishnuraj.newsfeed.data.repository.impl.NewsFeedRepositoryImpl
import com.vishnuraj.newsfeed.domain.impl.NewsFeedUseCaseImpl
import com.vishnuraj.newsfeed.infrastructure.NetworkManager
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class NewsFeedUseCaseTest {

    @MockK
    private lateinit var api: NewsFeedAPI

    @MockK
    private lateinit var networkManager: NetworkManager

    @SpyK
    private lateinit var repository: NewsFeedRepository
    private lateinit var useCase: NewsFeedUseCaseImpl

    @Before
    fun setup(){
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = spyk(NewsFeedRepositoryImpl(api))
        useCase = NewsFeedUseCaseImpl(repository, networkManager)
    }

    @Test
    fun fetchNewsFeed() {
        coEvery { networkManager.isNetworkAvailable() } answers { true }
        coEvery { repository.fetchNewsFeed() } answers { MockRepositoryResponse.getResponseData() }
        val result = runBlocking {
            useCase.fetchNewsFeed()
        }

        assertNotNull(result)
        val response = (result as Result.Success<*>).response
        assertNotNull(response as NewsFeedResponse)
        assertEquals(response.title, "About Canada")
        assertEquals(response.newsList.size, 13)
    }

    @Test
    fun networkConnectionError(){
        coEvery { networkManager.isNetworkAvailable() } answers { false }
        coEvery { repository.fetchNewsFeed() } answers { MockRepositoryResponse.getResponseData() }

        val result = runBlocking {
            useCase.fetchNewsFeed()
        }

        assertNotNull(result)
        val error = (result as Result.Failure).error
        assertNotNull(error as NetworkConnectionError)
    }

    @Test
    fun newsFeedError() {
        coEvery { networkManager.isNetworkAvailable() } answers { true }
        coEvery { repository.fetchNewsFeed() } answers { Result.Failure(NewsFeedError("server Error"))}

        val result = runBlocking {
            useCase.fetchNewsFeed()
        }

        assertNotNull(result)
        val error = (result as Result.Failure).error
        assertNotNull(error as NewsFeedError)
    }
}