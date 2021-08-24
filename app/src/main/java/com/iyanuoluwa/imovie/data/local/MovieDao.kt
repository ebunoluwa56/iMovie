package com.iyanuoluwa.imovie.data.local

import androidx.room.*
import com.iyanuoluwa.imovie.data.model.Result
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie : Result)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movie : List<Result>)

    @Query("SELECT * FROM movies ORDER BY id DESC")
    fun getMovieDatabase() : Flow<List<Result>>
}