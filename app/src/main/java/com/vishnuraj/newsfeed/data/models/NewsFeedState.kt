package com.vishnuraj.newsfeed.data.models

sealed class NewsFeedState {
    object Loading : NewsFeedState()
    data class NewsFeedData(val data: NewsFeedResponse) : NewsFeedState()
    data class ErrorData(val error: Error) : NewsFeedState()
}