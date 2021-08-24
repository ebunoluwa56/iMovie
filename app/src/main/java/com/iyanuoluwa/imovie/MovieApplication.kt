package com.iyanuoluwa.imovie

import android.app.Application
import com.iyanuoluwa.imovie.data.local.MovieDatabase
import com.iyanuoluwa.imovie.ui.main.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MovieApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { MovieDatabase.getDatabase(this) }
    val repository by lazy { MovieRepository(database.movieDao()) }
}