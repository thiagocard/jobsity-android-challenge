package com.jobsity.android.challenge.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import coil.load

object DataBindingAdapters {

    @JvmStatic
    @BindingAdapter("image_url")
    fun setImageUrl(view: ImageView, imageUrl: String?) {
        imageUrl?.let(view::load)
    }
    @JvmStatic
    @BindingAdapter("html_text")
    fun setHtmlText(view: TextView, htmlText: String?) {
        htmlText?.let {
            val span = HtmlCompat.fromHtml(
                it.replace("<p>", "").replace("</p>", ""),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            view.text = span
        }
    }

}