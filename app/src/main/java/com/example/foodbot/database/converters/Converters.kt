package com.example.foodbot.database.converters

import androidx.room.TypeConverter
import com.example.foodbot.database.entities.Recipe
import com.example.foodbot.database.model.FoodplanDay
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toStringList(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromRecipeList(value: String): List<Recipe> {
        val listType = object : TypeToken<List<Recipe>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toRecipeList(list: List<Recipe>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromFoodplanDayList(value: List<FoodplanDay>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toFoodplanDayList(value: String): List<FoodplanDay> {
        val listType = object : TypeToken<List<FoodplanDay>>() {}.type
        return Gson().fromJson(value, listType)
    }
}