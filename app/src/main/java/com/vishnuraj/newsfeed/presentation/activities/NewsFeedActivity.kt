package com.vishnuraj.newsfeed.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vishnuraj.newsfeed.R

/**
 * Activity class for showing the NewsFeed. This class loads the NewsFeed fragment
 * which will do the task to fetch the News feed.
 */
class NewsFeedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_feed)
    }

}
