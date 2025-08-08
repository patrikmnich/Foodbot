package com.example.foodbot.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.foodbot.R

/**
 * Enum values that represent the screens in the app.
 * Top level destinations are indicated by icon parameter.
 */
enum class Destination(
    @StringRes val title: Int,
    val icon: ImageVector? = null
) {
    Foodplan(title = R.string.app_name, icon = Icons.Default.Home),
    Recipes(title = R.string.recipes, icon = Icons.Default.Search),
    ShoppingList(title = R.string.shopping_list, icon = Icons.Default.ShoppingCart),
    RecipeDetailFoodplan(title = R.string.recipe_detail),
    RecipeDetailSearch(title = R.string.recipe_detail),
    NewRecipe(title = R.string.new_recipe),
}

val topLevelDestinations = setOf(
    Destination.Foodplan,
    Destination.Recipes,
    Destination.ShoppingList
)
