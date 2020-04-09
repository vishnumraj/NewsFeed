package com.vishnuraj.newsfeed.data.models

/**
 * Class representing the State of NewsFeed. There are 3 possible
 * states identified.
 * <p>
 *     1)Loading NewsFeed Loading is in progress
 *     2)NewsFeedData The NewsFeed is Fetched successfully
 *     3)ErrorData The NewsFeed fetching has resulted an error
 * </p>
 *
 * This state should be set as LiveData in ViewModel and UI should
 * observe for this state change and Update.
 */
sealed class NewsFeedState {
    object Loading : NewsFeedState()
    data class NewsFeedData(val data: NewsFeedResponse) : NewsFeedState()
    data class ErrorData(val error: Error) : NewsFeedState()
}