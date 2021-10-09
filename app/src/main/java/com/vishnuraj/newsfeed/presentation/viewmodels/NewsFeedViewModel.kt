package com.vishnuraj.newsfeed.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishnuraj.newsfeed.data.models.Result
import com.vishnuraj.newsfeed.data.models.NewsFeedResponse
import com.vishnuraj.newsfeed.data.models.NewsFeedState
import com.vishnuraj.newsfeed.domain.impl.NewsFeedUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel class for NewsFeed. This class execute the NewsFeed UseCase and
 * update the NewsFeedState Live Data based on the response UseCase.
 */
@HiltViewModel
class NewsFeedViewModel @Inject constructor(private val useCase: NewsFeedUseCaseImpl) : ViewModel() {

    var newsFeedState = MutableLiveData<NewsFeedState>().apply { value = NewsFeedState.Loading }

    /**
     * Method responsible for fetching the NewsFeed data and update the NewsFeed State
     * Live Data. The NewsFeed UseCase execution is carried out in viewModelScope.
     */
    fun fetchNewsFeed() {
        viewModelScope.launch {
            newsFeedState.value = NewsFeedState.Loading
            when (val result = useCase.fetchNewsFeed()) {
                is Result.Success<*> -> {
                    newsFeedState.value =
                        NewsFeedState.NewsFeedData(result.response as NewsFeedResponse)
                }
                is Result.Failure -> {
                    newsFeedState.value = NewsFeedState.ErrorData(result.error)
                }
            }
        }
    }
}