package com.example.cinereview.extensions

import android.view.View
import android.widget.ImageView
import coil.load
import com.example.cinereview.R
import java.time.Duration
import java.time.LocalTime

fun ImageView.loadImage(url: String? = null) {
    if (url != null) {
        visible()

        load(url) {
            crossfade(1000)
            error(R.drawable.erro)
            placeholder(R.drawable.erro)
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

fun Long.formatMinsToHour(): String {
    val movieHours = LocalTime.MIN.plus(
        Duration.ofMinutes(this)
    )
    return "$movieHours h"
}

fun String.validateDuration(): Long {
    return if (this.isBlank()) 0L
    else this.toLong()
}

fun String.validateGenre(): String{
    return if(this == "-----") ""
    else this
}

fun String.validadeRating(): Double{
    return if(this.isBlank()) 0.0
    else this.toDouble()
}