package com.iyanuoluwa.imovie

import android.app.Application
import com.iyanuoluwa.imovie.data.local.MovieDatabase
import com.iyanuoluwa.imovie.data.remote.MovieApi
import com.iyanuoluwa.imovie.ui.main.MovieRepository
import com.iyanuoluwa.imovie.util.BASE_URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { MovieDatabase.getDatabase(this) }
    private val api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }
    val repository by lazy { MovieRepository(database.movieDao(), api) }
}