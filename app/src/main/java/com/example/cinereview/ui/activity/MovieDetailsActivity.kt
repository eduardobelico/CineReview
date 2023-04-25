package com.example.cinereview.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.cinereview.R
import com.example.cinereview.constants.MOVIE_ID_KEY
import com.example.cinereview.database.AppDatabase
import com.example.cinereview.databinding.ActivityMovieDetailsBinding
import com.example.cinereview.extensions.formatMinsToHour
import com.example.cinereview.extensions.loadImage
import com.example.cinereview.model.Movie

class MovieDetailsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMovieDetailsBinding.inflate(layoutInflater)
    }
    private val movieDao by lazy {
        AppDatabase.instancia(this).movieDao()
    }
    private val toolbar by lazy {
        binding.detailsToolbar
    }
    private var movieId: Long = 0L
    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(toolbar)
        loadMovie()
    }

    override fun onResume() {
        super.onResume()
        getMovie()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_edit_movie, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.details_movie_remove -> {
                movie?.let {
                    movieDao.removeMovie(it)
                    finish()
                }
            }
            R.id.details_movie_edit -> {
                Intent(this, MovieFormActivity::class.java).apply {
                    putExtra(MOVIE_ID_KEY, movieId)
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadMovie() {
        movieId = intent.getLongExtra(MOVIE_ID_KEY, 0)
    }

    private fun fillDetails(loadedMovie: Movie) {
        with(binding) {
            detailsImage.loadImage(loadedMovie.image)
            detailsName.text = loadedMovie.name
            detailsYear.text = loadedMovie.year.toString()
            detailsDuration.text = loadedMovie.duration.formatMinsToHour()
            detailsGenreone.text = loadedMovie.genreOne
            detailsGenretwo.text = loadedMovie.genreTwo
            detailsRating.text = loadedMovie.rating.toString()
            detailsDescription.text = loadedMovie.description
            detailsReview.text = loadedMovie.review
        }
    }

    private fun getMovie() {
        movie = movieDao.searchById(movieId)
        movie?.let {
            fillDetails(it)
            toolbar.title = it.name
        } ?: finish()
    }

}