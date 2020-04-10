package com.vishnuraj.newsfeed.base.data.extensions

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso

/**
 * Method for loading Image from url string. Uses Picasso library
 * to load the image into the ImageView.
 *
 * @param context Context
 * @param imageUri ImageUrl
 */
fun ImageView.loadImageUrl(context: Context, imageUri: String) {
    Picasso.with(context).load(imageUri).into(this)
}