package com.iyanuoluwa.imovie.data.local

import androidx.room.*
import com.iyanuoluwa.imovie.data.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie : Movie)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movie : List<Movie>)

    @Query("SELECT * FROM movies ORDER BY id DESC LIMIT 20")
    suspend fun getMovies() : List<Movie>
}