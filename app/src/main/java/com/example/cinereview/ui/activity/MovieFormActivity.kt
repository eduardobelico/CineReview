package com.example.cinereview.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.example.cinereview.database.AppDatabase
import com.example.cinereview.databinding.ActivityMovieFormBinding
import com.example.cinereview.model.Movie


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
//        setSpinnerOne()
//        setSpinnerTwo()
        setSaveButton()
    }

//    private fun setSpinnerOne() {
//        binding.activityMovieFormGenreFirstSpinner.onItemSelectedListener =
//            object : OnItemSelectedListener {
//                override fun onItemSelected(
//                    adapterView: AdapterView<*>?,
//                    view: View?,
//                    position: Int,
//                    spinnerId: Long
//                ) {
//
//                }
//
//                override fun onNothingSelected(p0: AdapterView<*>?) {
//
//                }
//            }
//    }
//
//    private fun setSpinnerTwo() {
//        binding.activityMovieFormGenreSecondSpinner.onItemSelectedListener =
//            object : OnItemSelectedListener {
//                override fun onItemSelected(
//                    adapterView: AdapterView<*>?,
//                    view: View?,
//                    position: Int,
//                    spinnerId: Long
//                ) {
//                }
//                override fun onNothingSelected(p0: AdapterView<*>?) {
//                }
//            }
//    }

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
        val genreOne = binding.activityMovieFormGenreFirstSpinner.selectedItem.toString()
        val genreTwo = binding.activityMovieFormGenreSecondSpinner.selectedItem.toString()
        val durationToText = binding.activityMovieFormDuration.text.toString()
        val duration = if (durationToText.isBlank()){
            0
        } else {
           durationToText.toInt()
        }
        val description = binding.activityMovieFormDescription.text.toString()
        val review = binding.activityMovieFormReview.text.toString()
        val ratingToText = binding.activityMovieFormRating.text.toString()
        val rating = if (ratingToText.isBlank()){
            0.0
        } else {
            ratingToText.toDouble()
        }

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
