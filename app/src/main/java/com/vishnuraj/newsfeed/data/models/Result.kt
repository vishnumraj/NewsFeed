/*
 * Copyright (c) 2021. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.vishnuraj.newsfeed.data.models

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