package com.iyanuoluwa.imovie.data

import com.iyanuoluwa.imovie.api.MovieJson
import retrofit2.http.GET

// https://api.themoviedb.org/3/movie/upcoming?api_key=68f286331e8795bd4addf043c1e8423d&language=en-US

interface ApiUpcoming {
    @GET("movie/movie/upcoming?api_key=68f286331e8795bd4addf043c1e8423d&language=en-US")
    suspend fun getUpcomingMovies (): MovieJson
}