package com.example.cinereview.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cinereview.database.dao.MovieDao
import com.example.cinereview.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = true)

abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        @Volatile
        private lateinit var db: AppDatabase

        fun instancia(context: Context): AppDatabase {
            if (::db.isInitialized) return db
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "CineReview.db"
            ).allowMainThreadQueries()
                .build().also {
                    db = it
                }
        }
    }
}