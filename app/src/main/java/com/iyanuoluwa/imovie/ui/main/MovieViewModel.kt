package com.iyanuoluwa.imovie.ui.main

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.*
import com.iyanuoluwa.imovie.data.model.Category
import com.iyanuoluwa.imovie.data.model.Movie
import com.iyanuoluwa.imovie.util.Resource
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    fun getMovies(page: Int, limit: Int, category: Category): LiveData<Resource<List<Movie>>> {
        return repository.getMovies(page, limit, category).asLiveData()
    }

    private suspend fun mergeCategoriesAndUpdate(movieWithNewCategory: Movie) {
        val movieWithSavedCategories = repository.getMovie(movieWithNewCategory.id)
        if (!movieWithSavedCategories.categories.contains(movieWithNewCategory.categories.first())) {
            movieWithSavedCategories.categories.add(movieWithNewCategory.categories.first())
            repository.updateMovie(movieWithSavedCategories)
        }
    }

    fun insertMovies(movies: List<Movie>) {
        viewModelScope.launch {
            repository.insertMovies(movies)
        }
    }

    fun insertMovie(movie: Movie) {
        viewModelScope.launch {
            try {
                repository.insertMovie(movie)
            } catch (exception: SQLiteConstraintException) {
                mergeCategoriesAndUpdate(movie)
            }
        }
    }

}

class ViewModelFactory(private val repository: MovieRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovieViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}