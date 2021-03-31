package com.iyanuoluwa.imovie.data

import com.iyanuoluwa.imovie.api.MovieJson
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//const val API_KEY = "68f286331e8795bd4addf043c1e8423d"

// https://api.themoviedb.org/3/movie/now_playing?api_key=68f286331e8795bd4addf043c1e8423d&language=en-US&page=1

interface ApiNowPlaying {
    @GET("movie/now_playing?api_key=68f286331e8795bd4addf043c1e8423d&language=en-US")
    fun getMoviesPlaying(): Call<MovieJson>
}