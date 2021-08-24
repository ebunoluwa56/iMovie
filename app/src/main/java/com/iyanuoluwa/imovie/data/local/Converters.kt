package com.iyanuoluwa.imovie.data.local

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun listFromString(intListString: String): List<Int> {
        return intListString.split("|").map { it.toInt() }
    }

    @TypeConverter
    fun listToString(stringList: List<Int>): String {
        return stringList.joinToString(separator = "|")
    }
}