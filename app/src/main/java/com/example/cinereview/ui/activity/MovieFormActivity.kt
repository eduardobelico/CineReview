package com.example.cinereview.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cinereview.database.AppDatabase
import com.example.cinereview.databinding.ActivityMovieFormBinding
import com.example.cinereview.extensions.loadImage
import com.example.cinereview.extensions.validadeRating
import com.example.cinereview.extensions.validateDuration
import com.example.cinereview.extensions.validateGenre
import com.example.cinereview.model.Movie
import com.example.cinereview.ui.dialog.MovieFormImageDialog


class MovieFormActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMovieFormBinding.inflate(layoutInflater)
    }

    private val movieDao by lazy {
        AppDatabase.instancia(this).movieDao()
    }

    private var movieId = 0L
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSaveButton()
        title = "Adicionar Filme"
        binding.activityMovieFormImage.setOnClickListener() {
            MovieFormImageDialog(this)
                .showDialog(url) { image ->
                    url = image
                    binding.activityMovieFormImage.loadImage(url)
                }
        }
    }

    private fun setSaveButton() {
        val saveButton = binding.activityMovieFormSaveButton

        saveButton.setOnClickListener {
            val newMovie = createMovie()
                movieDao.saveMovie(newMovie)
                finish()
        }
    }

    private fun createMovie(): Movie {
        val name =  binding.activityMovieFormName.text.toString()
        val genreOneField = binding.activityMovieFormGenreFirstSpinner.selectedItem.toString()
        val genreOne = genreOneField.validateGenre()
        val genreTwoField = binding.activityMovieFormGenreSecondSpinner.selectedItem.toString()
        val genreTwo = genreTwoField.validateGenre()
        val durationToText = binding.activityMovieFormDuration.text.toString()
        val duration = durationToText.validateDuration()
        val description = binding.activityMovieFormDescription.text.toString()
        val review = binding.activityMovieFormReview.text.toString()
        val ratingToText = binding.activityMovieFormRating.text.toString()
        val rating = ratingToText.validadeRating()

        return Movie(
            id = movieId,
            name = name,
            genreOne = genreOne,
            genreTwo = genreTwo,
            duration = duration,
            description = description,
            review = review,
            rating = rating,
            image = url
        )

    }


}
