package com.iyanuoluwa.imovie.ui.main

import androidx.lifecycle.*
import com.iyanuoluwa.imovie.data.model.Category
import com.iyanuoluwa.imovie.data.model.Movie
import com.iyanuoluwa.imovie.util.Resource
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    fun getMovies(page: Int, limit: Int, category: Category): LiveData<Resource<List<Movie>>> {
        return repository.getMovies(page, limit, category).asLiveData()
    }

    fun insertMovies(movies: List<Movie>) {
        viewModelScope.launch {
            repository.insertMovies(movies)
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