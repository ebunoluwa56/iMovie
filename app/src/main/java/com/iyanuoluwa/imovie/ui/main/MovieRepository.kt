package com.iyanuoluwa.imovie.ui.main

import androidx.annotation.WorkerThread
import com.iyanuoluwa.imovie.data.local.MovieDao
import com.iyanuoluwa.imovie.data.model.Result
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao : MovieDao) {

    val allMovies : Flow<List<Result>> = movieDao.getMovieDatabase()

    @WorkerThread
    suspend fun insertMovies(movies : List<Result>) {
        movieDao.insertAllMovies(movies)
    }

}