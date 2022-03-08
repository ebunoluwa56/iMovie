package com.iyanuoluwa.imovie.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iyanuoluwa.imovie.data.model.Category
import java.io.File.separator

class Converters {

    @TypeConverter
    fun intListFromString(intListString: String): List<Int> {
        return intListString.split("|").map { it.toInt() }
    }

    @TypeConverter
    fun intListToString(stringList: List<Int>): String {
        return stringList.joinToString(separator = "|")
    }

    @TypeConverter
    fun categoryListFromString(categoryList : String) : List<Category> {
        return categoryList.split("|").map { Category.fromCategoryName(it) }
    }

    @TypeConverter
    fun categoryListToString(stringLists : List<Category>) : String {
        return stringLists.map { it.categoryName }.joinToString(separator = "|")
    }
}