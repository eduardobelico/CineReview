package com.example.cinereview.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cinereview.constants.MOVIE_ID_KEY
import com.example.cinereview.database.AppDatabase
import com.example.cinereview.databinding.ActivityMovieFormBinding
import com.example.cinereview.extensions.*
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
        tryToLoadMovie()
    }

    override fun onResume() {
        super.onResume()
        tryToFetchMovie()
    }

    private fun tryToLoadMovie() {
        movieId = intent.getLongExtra(MOVIE_ID_KEY, 0L)
    }

    private fun tryToFetchMovie() {
        movieDao.searchById(movieId)?.let {
            title = "Alterar Filme"
            fillDetails(it)
        }
    }

    private fun fillDetails(movie: Movie) {
        url = movie.image
        with(binding) {
            activityMovieFormImage.loadImage(movie.image)
            activityMovieFormName.setText(movie.name)
            activityMovieFormYear.setText(movie.year.toString())
            activityMovieFormDuration.setText(movie.duration.toString())
            activityMovieFormDescription.setText(movie.description)
            activityMovieFormReview.setText(movie.review)
            activityMovieFormRating.setText(movie.rating.toString())
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
        val name = binding.activityMovieFormName.text.toString()
        val genreOneField = binding.activityMovieFormGenreFirstSpinner.selectedItem.toString()
        val genreOne = genreOneField.validateGenre()
        val genreTwoField = binding.activityMovieFormGenreSecondSpinner.selectedItem.toString()
        val genreTwo = genreTwoField.validateGenre()
        val yearToText = binding.activityMovieFormYear.text.toString()
        val year = yearToText.validadeYear()
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
            year = year,
            duration = duration,
            description = description,
            review = review,
            rating = rating,
            image = url
        )
    }

}
