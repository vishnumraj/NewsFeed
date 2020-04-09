package com.vishnuraj.newsfeed.base.data.models

/**
 * Sealed call representing the Success and Failure response. This class
 * can be used to represent the Response Data
 */
sealed class Result {

    /**
     * Data class representing the Success response.
     *
     * @param response The actual response object
     * @return Result object
     */
    data class Success<RESPONSE>(val response: RESPONSE) : Result()

    /**
     * Data class representing the Failure response.
     *
     * @param error Kotlin Error object
     * @return Result
     */
    data class Failure(val error: Error) : Result()
}