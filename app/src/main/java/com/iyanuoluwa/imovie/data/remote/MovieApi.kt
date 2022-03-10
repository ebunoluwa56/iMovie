package com.iyanuoluwa.imovie.data.remote

import com.iyanuoluwa.imovie.data.model.Credits
import com.iyanuoluwa.imovie.data.model.MovieResponseObject
import com.iyanuoluwa.imovie.util.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/{movie_id}/credits?api_key=${API_KEY}&language=en-US")
    fun getMovieCredits(
        @Path("movie_id") movieId: Int
    ): Call<Credits>

    @GET("movie/{category}?api_key=${API_KEY}&language=en-US")
    suspend fun getMovies(
        @Path("category") string: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): MovieResponseObject
}
