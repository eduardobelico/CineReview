package com.example.cinereview.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val genreOne: String,
    val genreTwo: String,
    val duration: Long,
    val description: String,
    val review: String,
    val rating: Double,
    val image: String? = null
)