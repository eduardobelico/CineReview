package com.example.cinereview.database.dao

import androidx.room.*
import com.example.cinereview.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM Movie")
    fun searchMovie(): List<Movie>

    @Query("SELECT * FROM Movie WHERE id = :id")
    fun searchById(id: Long) : Movie?

    @Query("SELECT * FROM Movie WHERE name LIKE :searchQuery")
    fun searchMovieName(searchQuery: String): List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovie(movie: Movie)

    @Delete
    fun removeMovie(movie: Movie)
}