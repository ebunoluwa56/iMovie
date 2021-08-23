package com.iyanuoluwa.imovie.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao() : MovieDao

    companion object {

        private var INSTANCE : MovieDatabase? = null

        fun getDatabase(context: Context) : MovieDatabase {

            return INSTANCE ?: kotlin.synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "iMovie"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}