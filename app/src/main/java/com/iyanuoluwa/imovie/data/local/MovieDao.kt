package com.iyanuoluwa.imovie.data.local

import androidx.room.*
import com.iyanuoluwa.imovie.data.model.Category
import com.iyanuoluwa.imovie.data.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMovie(movie : Movie)

    @Update
    suspend fun updateMovie(movie: Movie)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movie : List<Movie>)

    @Query("SELECT * FROM movies WHERE categories LIKE '%'||:requiredCategory||'%' ORDER BY id DESC LIMIT 20")
    suspend fun getMovies(requiredCategory: Category) : List<Movie>

    @Query("SELECT * FROM movies WHERE id = :id")
    suspend fun getMovie(id : Int) : Movie
}