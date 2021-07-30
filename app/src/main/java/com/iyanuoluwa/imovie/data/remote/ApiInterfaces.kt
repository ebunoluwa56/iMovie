package com.iyanuoluwa.imovie.data.remote

import com.iyanuoluwa.imovie.data.model.Credits
import com.iyanuoluwa.imovie.data.model.MovieJson
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiCredits {
    @GET("movie/{movie_id}/credits?api_key=68f286331e8795bd4addf043c1e8423d&language=en-US")
    fun getMovieCredits(
        @Path("movie_id") movieId: Int
    ): Call<Credits>
}

interface ApiNowPlaying {
    @GET("movie/now_playing?api_key=68f286331e8795bd4addf043c1e8423d&language=en-US")
    fun getMoviesPlaying(
        @Query("page") page : Int,
        @Query("limit") limit : Int
    ): Call<MovieJson>
}

interface ApiPopular {
    @GET("movie/popular?api_key=68f286331e8795bd4addf043c1e8423d&language=en-US")
    fun getPopularMovies(
        @Query("page") page : Int,
        @Query("limit") limit : Int
    ) : Call<MovieJson>
}

interface ApiTopRated {
    @GET("movie/top_rated?api_key=68f286331e8795bd4addf043c1e8423d&language=en-US")
    fun getTopRatedMovies(
        @Query("page") page : Int,
        @Query("limit") limit : Int
    ) : Call<MovieJson>
}

interface ApiUpcoming {
    @GET("movie/upcoming?api_key=68f286331e8795bd4addf043c1e8423d&language=en-US")
    fun getUpcomingMovies(
        @Query("page") page : Int,
        @Query("limit") limit : Int
    ) : Call<MovieJson>
}
