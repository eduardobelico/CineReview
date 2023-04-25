package com.example.cinereview.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.cinereview.R
import com.example.cinereview.constants.MOVIE_ID_KEY
import com.example.cinereview.database.AppDatabase
import com.example.cinereview.databinding.ActivityMovieListBinding
import com.example.cinereview.ui.recyclerview.adapter.MovieListAdapter
import com.google.android.material.snackbar.Snackbar

class MovieListActivity : AppCompatActivity(),
    SearchView.OnQueryTextListener {


    private val binding by lazy {
        ActivityMovieListBinding.inflate(layoutInflater)
    }
    private val movieDao by lazy {
        AppDatabase.instancia(this).movieDao()
    }
    private val adapter = MovieListAdapter(context = this@MovieListActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setRecyclerView()
        setFab()
    }

    override fun onResume() {
        super.onResume()
        val db = AppDatabase.instancia(this)
        val movieDao = db.movieDao()
        adapter.setData(movieDao.searchMovie())
    }

    private fun setRecyclerView() {
        val recyclerView = binding.activityMovieListRecyclerView
        recyclerView.adapter = adapter
        swipeToDelete(recyclerView)
        goToDetails()
    }

    private fun setFab() {
        val fab = binding.activityMovieListFab
        fab.setOnClickListener {
            toMovieFormActivity()
        }
    }

    private fun toMovieFormActivity() {
        val intent = Intent(this, MovieFormActivity::class.java)
        startActivity(intent)
    }

    private fun goToDetails() {
        adapter.clickOnMovie = {
            val intent = Intent(this, MovieDetailsActivity::class.java).apply {
                putExtra(MOVIE_ID_KEY, it.id)
            }
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_movie, menu)

        val search = menu?.findItem(R.id.movie_list_search_menu)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String?) {
        val searchQuery = "%$query%"
        val movieList = movieDao.searchMovieName(searchQuery)
        if (movieList.isNotEmpty()) {
            adapter.setData(movieList)
        } else {
            adapter.setData(movieList)
            Snackbar.make(binding.root, "Filme não encontrado", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val movieToDelete = adapter.getItem(position)
                adapter.excludeItem(position)
                adapter.notifyItemRemoved(viewHolder.bindingAdapterPosition)
                movieDao.removeMovie(movieToDelete)
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

}