package com.jobsity.android.challenge.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load

object DataBindingAdapters {

    @JvmStatic
    @BindingAdapter("image_url")
    fun setPaddingLeft(view: ImageView, imageUrl: String) {
        view.load(imageUrl)
    }

}