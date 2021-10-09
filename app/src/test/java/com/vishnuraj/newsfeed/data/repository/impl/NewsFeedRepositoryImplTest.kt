package com.vishnuraj.newsfeed.data.repository.impl

import com.vishnuraj.newsfeed.MockRepositoryResponse
import com.vishnuraj.newsfeed.data.api.NewsFeedAPI
import com.vishnuraj.newsfeed.data.models.NewsFeedResponse
import com.vishnuraj.newsfeed.data.models.Result
import com.vishnuraj.newsfeed.data.repository.NewsFeedRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class NewsFeedRepositoryImplTest {
    @MockK
    private lateinit var api: NewsFeedAPI

    private lateinit var repository: NewsFeedRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = NewsFeedRepositoryImpl(api)
    }

    @Test
    fun fetchNewsFeed() {
        coEvery { api.fetchNewsFeed() } answers { MockRepositoryResponse.getRetrofitResponse() }
        val result = runBlocking {
            repository.fetchNewsFeed()
        }

        assertNotNull(result)
        val response = (result as Result.Success<*>).response
        assertNotNull(response as NewsFeedResponse)
        assertEquals(response.title, "About Canada")
        assertEquals(response.newsList.size, 14)
    }
}