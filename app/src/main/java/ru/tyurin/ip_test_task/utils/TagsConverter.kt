package ru.tyurin.ip_test_task.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class TagsConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromTagsList(tags: List<String>?): String {
        return gson.toJson(tags ?: emptyList<String>())
    }

    @TypeConverter
    fun toTagsList(tagsString: String?): List<String> {
        return gson.fromJson(tagsString, object : TypeToken<List<String>>() {}.type) ?: emptyList()
    }


}