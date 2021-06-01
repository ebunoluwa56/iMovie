package com.iyanuoluwa.imovie.api2


import com.google.gson.annotations.SerializedName

data class Credits(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)