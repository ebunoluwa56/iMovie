package com.iyanuoluwa.imovie.data.local

import androidx.annotation.WorkerThread
import com.iyanuoluwa.imovie.data.model.Result
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao : MovieDao) {

    val allMovies : Flow<List<Result>> = movieDao.getMovieDatabase()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(movie : Result) {
        movieDao.insert(movie)
    }
}