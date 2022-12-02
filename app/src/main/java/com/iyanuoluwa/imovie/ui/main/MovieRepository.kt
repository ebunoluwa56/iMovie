package com.iyanuoluwa.imovie.ui.main

import androidx.annotation.WorkerThread
import com.iyanuoluwa.imovie.data.local.MovieDao
import com.iyanuoluwa.imovie.data.model.Cast
import com.iyanuoluwa.imovie.data.model.Category
import com.iyanuoluwa.imovie.data.model.Movie
import com.iyanuoluwa.imovie.data.remote.MovieApi
import com.iyanuoluwa.imovie.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieDao: MovieDao,
    private val movieApi: MovieApi
) {

    @WorkerThread
    suspend fun insertMovies(movies: List<Movie>) {
        movieDao.insertAllMovies(movies)
    }

    suspend fun insertMovie(movie: Movie) {
        movieDao.insertMovie(movie)
    }

    suspend fun updateMovie(movie: Movie) {
        movieDao.updateMovie(movie)
    }

    suspend fun getMovie(id: Int): Movie = movieDao.getMovie(id)

    private suspend fun getCastNetWork(id: Int) = movieApi.getMovieCredits(id).cast

    fun getCast(id: Int) : Flow<Resource<List<Cast>>> = flow {
        emit(Resource.Loading)
        try {
            val response = getCastNetWork(id)
            emit(Resource.Success(response))
        } catch (e : Exception) {
            emit(Resource.Failure(e))
        }
    }

    private suspend fun getMoviesLocal(category: Category): List<Movie> =
        movieDao.getMovies(category)

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
            emit(Resource.Failure(e))
        }
    }
}