package com.iyanuoluwa.imovie.data

import com.iyanuoluwa.imovie.api.MovieJson
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

// https://api.themoviedb.org/3/movie/upcoming?api_key=68f286331e8795bd4addf043c1e8423d&language=en-US

interface ApiUpcoming {
    @GET("movie/upcoming?api_key=68f286331e8795bd4addf043c1e8423d&language=en-US")
    fun getUpcomingMovies (
            @Query("page") page : Int,
            @Query("limit") limit : Int
    ): Call<MovieJson>
}