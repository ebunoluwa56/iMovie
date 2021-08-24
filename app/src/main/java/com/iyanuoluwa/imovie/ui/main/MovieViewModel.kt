package com.iyanuoluwa.imovie.ui.main

import androidx.lifecycle.*
import com.iyanuoluwa.imovie.data.model.Result
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    val allMovies : LiveData<List<Result>> = repository.allMovies.asLiveData()

    fun insertMovies(movies: List<Result>) {
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