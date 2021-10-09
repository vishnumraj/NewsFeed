package com.vishnuraj.newsfeed.data.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.vishnuraj.newsfeed.R

/**
 * Method for loading Image from url string. Uses Picasso library
 * to load the image into the ImageView.
 *
 * @param imageUri ImageUrl
 */
fun ImageView.loadImageUrl(imageUri: String) {

    Glide.with(context)
        .load(imageUri)
        .centerCrop()
        .placeholder(R.drawable.image_placeholder)
        .into(this)
}