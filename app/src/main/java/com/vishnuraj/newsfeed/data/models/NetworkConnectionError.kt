package com.vishnuraj.newsfeed.data.models

class NetworkConnectionError : Error() {
    override val message = "Network Connection Error. Please check you connectivity"
}