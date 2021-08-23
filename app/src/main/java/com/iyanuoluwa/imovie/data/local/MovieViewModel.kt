package com.iyanuoluwa.imovie.data.local

import androidx.lifecycle.*
import com.iyanuoluwa.imovie.data.model.Result
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    val allMovie : LiveData<List<Result>> = repository.allMovies.asLiveData()

    fun insert(movie : Result) = viewModelScope.launch {
        repository.insert(movie)
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