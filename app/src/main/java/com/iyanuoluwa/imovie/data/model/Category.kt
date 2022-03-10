package com.iyanuoluwa.imovie.data.model

enum class Category(val categoryName: String) {
    NOW_PLAYING("now_playing"),
    POPULAR("popular"),
    TOP_RATED("top_rated"),
    UPCOMING("upcoming");

    companion object {
        fun fromCategoryName(categoryName: String): Category =
            values().find { categoryName == it.categoryName } ?: NOW_PLAYING
    }

}
