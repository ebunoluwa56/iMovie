package com.iyanuoluwa.imovie.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.iyanuoluwa.imovie.data.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao() : MovieDao

    companion object {

        private var INSTANCE : MovieDatabase? = null

        fun getDatabase(context: Context) : MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "iMovie.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}