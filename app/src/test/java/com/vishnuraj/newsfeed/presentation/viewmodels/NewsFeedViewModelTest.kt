package com.vishnuraj.newsfeed.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vishnuraj.newsfeed.MockRepositoryResponse
import com.vishnuraj.newsfeed.data.api.NewsFeedAPI
import com.vishnuraj.newsfeed.data.models.NetworkConnectionError
import com.vishnuraj.newsfeed.data.models.NewsFeedError
import com.vishnuraj.newsfeed.data.models.NewsFeedState
import com.vishnuraj.newsfeed.data.repository.NewsFeedRepository
import com.vishnuraj.newsfeed.data.repository.impl.NewsFeedRepositoryImpl
import com.vishnuraj.newsfeed.domain.impl.NewsFeedUseCaseImpl
import com.vishnuraj.newsfeed.infrastructure.NetworkManager
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsFeedViewModelTest {

    @MockK
    private lateinit var api: NewsFeedAPI

    @MockK
    private lateinit var networkManager: NetworkManager

    private lateinit var repository: NewsFeedRepository
    private lateinit var useCase: NewsFeedUseCaseImpl
    private lateinit var viewModel: NewsFeedViewModel

    private val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)

        MockKAnnotations.init(this, relaxUnitFun = true)

        repository = spyk(NewsFeedRepositoryImpl(api))
        useCase = NewsFeedUseCaseImpl(repository,networkManager)
        viewModel = NewsFeedViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchNewsFeed() {
        coEvery { networkManager.isNetworkAvailable() } answers { true }
        coEvery { repository.fetchNewsFeed() } answers { MockRepositoryResponse.getResponseData() }

        assertEquals(viewModel.newsFeedState.value, NewsFeedState.Loading)

        dispatcher.runBlockingTest {
            viewModel.fetchNewsFeed()
        }

        assertTrue(viewModel.newsFeedState.value is NewsFeedState.NewsFeedData)
        val response = (viewModel.newsFeedState.value as NewsFeedState.NewsFeedData).data

        assertNotNull(response)
        assertEquals(response.title, "About Canada")
        assertEquals(response.newsList.size, 13)
    }

    @Test
    fun networkConnectionError() {
        coEvery { networkManager.isNetworkAvailable() } answers { false }

        assertEquals(viewModel.newsFeedState.value, NewsFeedState.Loading)

        dispatcher.runBlockingTest {
            viewModel.fetchNewsFeed()
        }

        assertTrue(viewModel.newsFeedState.value is NewsFeedState.ErrorData)
        val response = (viewModel.newsFeedState.value as NewsFeedState.ErrorData).error

        assertNotNull(response as NetworkConnectionError)
    }

    @Test
    fun newsFeedError() {
        coEvery { networkManager.isNetworkAvailable() } answers { true }
        coEvery { repository.fetchNewsFeed() } answers { MockRepositoryResponse.getErrorData() }

        assertEquals(viewModel.newsFeedState.value, NewsFeedState.Loading)

        dispatcher.runBlockingTest {
            viewModel.fetchNewsFeed()
        }

        assertTrue(viewModel.newsFeedState.value is NewsFeedState.ErrorData)
        val response = (viewModel.newsFeedState.value as NewsFeedState.ErrorData).error

        assertNotNull(response as NewsFeedError)

    }

}