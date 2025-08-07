package com.example.foodbot.database.model

import com.example.foodbot.database.entities.Recipe
import com.example.foodbot.utils.Day
import com.google.gson.annotations.SerializedName

data class FoodplanDay(
    @SerializedName("id")
    val id: Int,

    @SerializedName("recipes_ids")
    val recipes: List<Recipe>
) {

    companion object {
        fun example() = FoodplanDay(
            Day.MONDAY.ordinal,
            listOf(
                Recipe.example(),
                Recipe.example(),
                Recipe.example(),
                Recipe.example(),
                Recipe.example(),
            )
        )
    }
}
