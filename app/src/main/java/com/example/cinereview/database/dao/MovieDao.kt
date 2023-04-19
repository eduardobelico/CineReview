package com.example.cinereview.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cinereview.model.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM Movie")
    fun searchMovie(): List<Movie>

    @Query("SELECT * FROM Movie WHERE id = :id")
    fun searchById(id: Long) : Movie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveMovie(movie: Movie)

    @Delete
    fun removeMovie(movie: Movie)
}