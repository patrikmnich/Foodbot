package com.example.foodbot.data

import com.example.foodbot.database.model.FoodType

data class NewRecipeState(
    val name: String = "",
    val types: Set<FoodType> = setOf(),
    val imageRes: String = "",
    val ingredients: List<String> = listOf(""),
    val steps: List<String> = listOf(""),
)
