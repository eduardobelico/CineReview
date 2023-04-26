package com.example.cinereview.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinereview.databinding.MovieCardBinding
import com.example.cinereview.extensions.formatMinsToHour
import com.example.cinereview.extensions.loadImage
import com.example.cinereview.model.Movie

class MovieListAdapter(
    val context: Context,
    var clickOnMovie: (movie: Movie) -> Unit = {}
) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    private val movieList = mutableListOf<Movie>()

    inner class MovieViewHolder(private val binding: MovieCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var movie: Movie

        fun bindView(movie: Movie) {
            this.movie = movie
            with(binding) {
                movieCardImage.loadImage(movie.image)
                movieCardName.text = movie.name
                movieCardGenreOne.text = movie.genreOne
                movieCardGenreTwo.text = movie.genreTwo
                movieCardDuration.text = movie.duration.formatMinsToHour()
                movieCardRating.text = movie.rating.toString()
            }
        }

        init {
            itemView.setOnClickListener {
                if (::movie.isInitialized) {
                    clickOnMovie(movie)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieCardBinding.inflate(inflater, parent, false)

        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bindView(movieList[position])

    fun setData(newMovieList: List<Movie>) {
        notifyItemRangeRemoved(0, movieList.size)
        movieList.clear()
        movieList.addAll(newMovieList)
        notifyItemInserted(newMovieList.size)
    }

    fun getItem(position: Int): Movie {
        return movieList[position]
    }

    fun excludeItem(position: Int) {
        movieList.removeAt(position)
    }
}