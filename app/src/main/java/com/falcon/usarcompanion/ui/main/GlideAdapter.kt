package com.falcon.usarcompanion.ui.main

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("iconURL")
fun bindImage(imgView: ImageView, imgURL: String?) {
    imgURL?.let {
        val imgUri = imgURL.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}