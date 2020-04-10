package com.vishnuraj.newsfeed.data.models

/**
 * Error class representing the NetworkConnectivity failure.
 */
class NetworkConnectionError : Error() {
    override val message = "Network Connection Error. Please check you connectivity"
}