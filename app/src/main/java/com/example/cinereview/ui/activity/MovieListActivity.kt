package com.example.cinereview.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.cinereview.database.AppDatabase
import com.example.cinereview.databinding.ActivityMovieListBinding
import com.example.cinereview.ui.recyclerview.adapter.MovieListAdapter

class MovieListActivity : AppCompatActivity() {

    private val adapter = MovieListAdapter()
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