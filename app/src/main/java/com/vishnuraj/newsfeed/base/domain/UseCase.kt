package com.vishnuraj.newsfeed.base.domain

/**
 * Generic Interface representing the UseCase. All app UseCase
 * has to implement this interface.
 */
interface UseCase<REQUEST, RESPONSE> {

    /**
     * Method to execute the UseCase. This has to be executed
     * within the Coroutine block.
     *
     * @param params The Input parameters
     * @return The response
     */
    suspend fun execute(params: REQUEST? = null): RESPONSE

}