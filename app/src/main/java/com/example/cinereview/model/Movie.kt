package com.example.cinereview.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val genre: String,
    val duration: String,
    val nota: Double,
    val imagem: String? = null
)