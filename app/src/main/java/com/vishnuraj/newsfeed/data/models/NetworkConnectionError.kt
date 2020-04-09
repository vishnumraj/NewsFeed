package com.vishnuraj.newsfeed.data.models

class NetworkConnectionError : Error() {
    override val message = "network Connection Error. Please check you connectivity"
}