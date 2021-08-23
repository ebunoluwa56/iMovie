package com.iyanuoluwa.imovie.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iyanuoluwa.imovie.data.model.Result
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie : Result)

    @Query("SELECT * FROM movies_table ORDER BY ids")
    fun getMovieDatabase() : Flow<List<Result>>
}