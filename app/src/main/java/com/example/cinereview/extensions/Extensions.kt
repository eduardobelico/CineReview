package com.example.cinereview.extensions

import android.view.View
import android.widget.ImageView
import coil.load
import com.example.cinereview.R

fun ImageView.loadImage(url: String? = null) {
    if (url != null) {
        visible()

        load(url) {
            fallback(R.drawable.erro)
            error(R.drawable.erro)
            placeholder(R.drawable.erro)
            crossfade(1000)
        }
    } else visibilityGone()
}

    fun View.visible() {
        visibility = View.VISIBLE
    }

    fun View.invisible() {
        visibility = View.INVISIBLE
    }

    fun View.visibilityGone() {
        visibility = View.GONE
    }
