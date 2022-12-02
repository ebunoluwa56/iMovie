package com.iyanuoluwa.imovie.ui.main

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.iyanuoluwa.imovie.data.model.Cast
import com.iyanuoluwa.imovie.data.model.Category
import com.iyanuoluwa.imovie.data.model.Movie
import com.iyanuoluwa.imovie.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    fun getMovies(page: Int, limit: Int, category: Category): LiveData<Resource<List<Movie>>> {
        return movieRepository.getMovies(page, limit, category).asLiveData()
    }

    fun getCast(id: Int): LiveData<Resource<List<Cast>>> {
        return movieRepository.getCast(id).asLiveData()
    }

    private suspend fun mergeCategoriesAndUpdate(movieWithNewCategory: Movie) {
        val movieWithSavedCategories = movieRepository.getMovie(movieWithNewCategory.id)
        if (!movieWithSavedCategories.categories.contains(movieWithNewCategory.categories.first())) {
            movieWithSavedCategories.categories.add(movieWithNewCategory.categories.first())
            movieRepository.updateMovie(movieWithSavedCategories)
        }
    }

    fun insertMovies(movies: List<Movie>) {
        viewModelScope.launch {
            movieRepository.insertMovies(movies)
        }
    }

    fun insertMovie(movie: Movie) {
        viewModelScope.launch {
            try {
                movieRepository.insertMovie(movie)
            } catch (exception: SQLiteConstraintException) {
                mergeCategoriesAndUpdate(movie)
            }
        }
    }

}