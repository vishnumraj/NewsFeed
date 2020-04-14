package com.vishnuraj.newsfeed.base.data.extensions

import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.vishnuraj.newsfeed.R

/**
 * Method for loading Image from url string. Uses Picasso library
 * to load the image into the ImageView.
 *
 * @param imageUri ImageUrl
 */
fun ImageView.loadImageUrl(imageUri: String) {
    Picasso.get()
        .load(imageUri)
        .placeholder(R.drawable.image_placeholder)
        .into(this)
}