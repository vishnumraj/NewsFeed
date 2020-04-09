package com.vishnuraj.newsfeed.data.models

/**
 * Error class Representing the Error encountered while
 * fetching the NewsFeed from server.
 */
class NewsFeedError(message: String) : Error(message) {}