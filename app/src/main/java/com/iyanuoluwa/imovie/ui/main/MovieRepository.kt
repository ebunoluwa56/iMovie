package com.iyanuoluwa.imovie.ui.main

import androidx.annotation.WorkerThread
import com.iyanuoluwa.imovie.data.local.MovieDao
import com.iyanuoluwa.imovie.data.model.Category
import com.iyanuoluwa.imovie.data.model.Movie
import com.iyanuoluwa.imovie.data.remote.MovieApi
import com.iyanuoluwa.imovie.util.Resource
import kotlinx.coroutines.flow.flow

class MovieRepository(
    private val movieDao: MovieDao,
    private val movieApi: MovieApi
) {

    @WorkerThread
    suspend fun insertMovies(movies: List<Movie>) {
        movieDao.insertAllMovies(movies)
    }

    private suspend fun getMoviesLocal(category: Category): List<Movie> = movieDao.getMovies(category)
    private suspend fun getMoviesNetwork(page: Int, limit: Int, category: Category) =
        movieApi.getMovies(category.categoryName, page, limit).movies

    fun getMovies(page: Int, limit: Int, category: Category) = flow {
        emit(Resource.Loading)

        // Only emit saved data on first page
        if (page == 1) emit(Resource.Success(getMoviesLocal(category)))

        try {
            val movies = getMoviesNetwork(page, limit, category)
//            if (page == 1) insertMovies(movies)
            emit(Resource.Success(movies))
        } catch (e: Exception) {
            emit(Resource.Failure<List<Movie>>(e))
        }
    }
}