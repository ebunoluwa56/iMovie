package com.iyanuoluwa.imovie.data

import com.iyanuoluwa.imovie.api2.Credits
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiCredits {
    @GET("movie/{movie_id}/credits?api_key=68f286331e8795bd4addf043c1e8423d&language=en-US")
    fun getMovieCredits(
        @Path("movie_id") movieId: Int
    ): Call<Credits>
}