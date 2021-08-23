package com.iyanuoluwa.imovie.data.local

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MovieApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { MovieDatabase.getDatabase(this) }
    private val repository by lazy { MovieRepository(database.movieDao()) }
}