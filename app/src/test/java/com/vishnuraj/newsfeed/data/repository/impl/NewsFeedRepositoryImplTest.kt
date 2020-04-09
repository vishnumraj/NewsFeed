package com.vishnuraj.newsfeed.data.repository.impl

import android.content.Context
import com.vishnuraj.newsfeed.base.data.Result
import com.vishnuraj.newsfeed.data.models.NewsFeedResponse
import com.vishnuraj.newsfeed.data.repository.NewsFeedRepository
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class NewsFeedRepositoryImplTest {
    private lateinit var context: Context
    private lateinit var repository: NewsFeedRepository

    @Before
    fun setUp() {
        context = mockk()
        repository = NewsFeedRepositoryImpl(context)
    }

    @Test
    fun fetchNewsFeed() {
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