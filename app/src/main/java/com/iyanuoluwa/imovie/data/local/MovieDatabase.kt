package com.iyanuoluwa.imovie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.iyanuoluwa.imovie.data.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        const val DATABASE_NAME: String = "imovie_database"
    }
}