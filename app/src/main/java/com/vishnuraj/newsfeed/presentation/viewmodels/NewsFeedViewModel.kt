package com.vishnuraj.newsfeed.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishnuraj.newsfeed.base.data.Result
import com.vishnuraj.newsfeed.data.models.NewsFeedResponse
import com.vishnuraj.newsfeed.data.models.NewsFeedState
import com.vishnuraj.newsfeed.domain.NewsFeedUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsFeedViewModel @Inject constructor(private val useCase: NewsFeedUseCase) : ViewModel() {

    var newsFeedState = MutableLiveData<NewsFeedState>().apply { value = NewsFeedState.Loading }

    fun fetchNewsFeed() {
        viewModelScope.launch {
            newsFeedState.value = NewsFeedState.Loading
            when (val result = useCase.execute()) {
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