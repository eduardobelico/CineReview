package com.example.cinereview.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.cinereview.databinding.MovieFormImageBinding
import com.example.cinereview.extensions.loadImage

class MovieFormImageDialog(private val context: Context) {

    fun showDialog(
        urlBase: String? = null,
        imageLoaded: (image: String) -> Unit
    ) {
        MovieFormImageBinding.inflate(LayoutInflater.from(context)).apply {
            urlBase?.let {
                movieFormDialogImage.loadImage(it)
                movieFormImageUrl.setText(it)
            }
            movieFormDialogLoadButton.setOnClickListener {
                val url = movieFormImageUrl.text.toString()
                movieFormDialogImage.loadImage(url)
            }

            AlertDialog.Builder(context)
                .setView(root)
                .setPositiveButton("Confirmar") { _, _ ->
                    val url = movieFormImageUrl.text.toString()
                    imageLoaded(url)
                }
                .setNegativeButton("Cancelar") { _, _ -> }
                .show()
        }
    }
}