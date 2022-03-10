package com.iyanuoluwa.imovie.data.model


import com.google.gson.annotations.SerializedName

data class MovieResponseObject(
    val dates: Dates,
    val page: Int,
    @SerializedName("results")
    val movies: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)