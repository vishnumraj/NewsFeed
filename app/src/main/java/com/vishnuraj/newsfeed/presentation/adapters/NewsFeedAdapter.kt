package com.vishnuraj.newsfeed.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vishnuraj.newsfeed.R
import com.vishnuraj.newsfeed.data.extensions.loadImageUrl
import com.vishnuraj.newsfeed.data.models.News

/**
 * Recycler View adapter class representing the NewsFeed data for the RecyclerView.
 *
 * @param context Context object
 * @param newsList The News Feed item
 */
class NewsFeedAdapter(private val context: Context, private val newsList: List<News>) :
    RecyclerView.Adapter<NewsFeedViewHolder>() {

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.news_feed_item, null)
        return NewsFeedViewHolder(itemView)
    }

    /**
     * Method for Binding the ViewHolder. Here the Views visibility is driven by the
     * existence of the data in the news feed.
     *
     * @param viewHolder The NewsFeedViewHolder
     * @param position The position in the list view
     */
    override fun onBindViewHolder(viewHolder: NewsFeedViewHolder, position: Int) {
        val news = newsList[position]
        news.title?.let {
            viewHolder.title.visibility = VISIBLE
            viewHolder.title.text = it
        } ?: run { viewHolder.title.visibility = GONE }
        news.description?.let {
            viewHolder.description.visibility = VISIBLE
            viewHolder.description.text = it
        } ?: run { viewHolder.description.visibility = GONE }
        news.imageUrl?.let {
            viewHolder.image.loadImageUrl(it)
        } ?: run { viewHolder.image.setImageDrawable(context.getDrawable(R.drawable.image_placeholder))}
    }

}

/**
 * Recycler ViewHolder class for holding the TextView for NewsTitle and News description
 * as well as holding the ImageView for News image.
 *
 * @param itemView The inflated View for the itemCell
 */
class NewsFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.newsTitle)
    val description: TextView = itemView.findViewById(R.id.newsDescription)
    val image: ImageView = itemView.findViewById(R.id.newsImage)
}