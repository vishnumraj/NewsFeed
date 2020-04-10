package com.vishnuraj.newsfeed.domain

import android.content.Context
import com.vishnuraj.newsfeed.base.data.models.Result
import com.vishnuraj.newsfeed.data.models.NetworkConnectionError
import com.vishnuraj.newsfeed.data.models.NewsFeedError
import com.vishnuraj.newsfeed.data.models.NewsFeedResponse
import com.vishnuraj.newsfeed.data.repository.NewsFeedRepository
import com.vishnuraj.newsfeed.data.repository.impl.NewsFeedRepositoryImpl
import io.mockk.coEvery
import io.mockk.impl.annotations.SpyK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class NewsFeedUseCaseTest {

    @SpyK
    private lateinit var repository: NewsFeedRepository
    private lateinit var context: Context
    private lateinit var useCase: NewsFeedUseCase
    @Before
    fun setup(){
        context = mockk()
        repository = spyk(NewsFeedRepositoryImpl(context))
        useCase = NewsFeedUseCase(repository)
    }

    @Test
    fun fetchNewsFeed() {
        coEvery { repository.isNetworkAvailable() } answers { true }
        val result = runBlocking {
            useCase.execute()
        }

        assertNotNull(result)
        val response = (result as Result.Success<*>).response
        assertNotNull(response as NewsFeedResponse)
        assertEquals(response.title, "About Canada")
        assertEquals(response.newsList.size, 13)
    }

    @Test
    fun networkConnectionError(){
        coEvery { repository.isNetworkAvailable() } answers { false }

        val result = runBlocking {
            useCase.execute()
        }

        assertNotNull(result)
        val error = (result as Result.Failure).error
        assertNotNull(error as NetworkConnectionError)
    }

    @Test
    fun newsFeedError() {
        coEvery { repository.isNetworkAvailable() } answers { true }
        coEvery { repository.fetchNewsFeed() } answers { Result.Failure(NewsFeedError("server Error"))}

        val result = runBlocking {
            useCase.execute()
        }

        assertNotNull(result)
        val error = (result as Result.Failure).error
        assertNotNull(error as NewsFeedError)
    }
}