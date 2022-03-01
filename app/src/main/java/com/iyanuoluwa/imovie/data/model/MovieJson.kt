package com.iyanuoluwa.imovie.data.model


import com.google.gson.annotations.SerializedName

data class MovieJson(
    val dates: Dates,
    val page: Int,
    val movies: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)