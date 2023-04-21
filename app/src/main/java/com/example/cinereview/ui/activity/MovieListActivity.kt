package com.example.cinereview.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cinereview.database.AppDatabase
import com.example.cinereview.databinding.ActivityMovieListBinding
import com.example.cinereview.ui.recyclerview.adapter.MovieListAdapter

class MovieListActivity : AppCompatActivity() {

    private val adapter = MovieListAdapter(context = this@MovieListActivity)
    private val binding by lazy {
        ActivityMovieListBinding.inflate(layoutInflater)
    }
    private val movieDao by lazy {
        AppDatabase.instancia(this).movieDao()
    }

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


}