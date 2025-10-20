package com.example.foodbot.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.foodbot.data.NewRecipeState
import com.example.foodbot.database.dao.FoodplanDao
import com.example.foodbot.database.dao.RecipesDao
import com.example.foodbot.database.entities.Recipe
import com.example.foodbot.database.model.FoodType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * [NewRecipeViewModel] holds information about a new recipe.
 */
@HiltViewModel
class NewRecipeViewModel @Inject constructor(
    val recipesDao: RecipesDao,
) : ViewModel() {

    companion object {
        private val TAG = NewRecipeViewModel::class.simpleName.toString()
    }

    var state by mutableStateOf(NewRecipeState())
        private set

    fun updateName(value: String) {
        state = state.copy(name = value)
    }

    fun addType(value: FoodType) {
        state = state.copy(types = state.types + value)
    }

    fun removeType(value: FoodType) {
        state = state.copy(types = state.types.filterNot { it == value }.toSet())
    }

    fun updateIngredient(index: Int, value: String) {
        val updated = state.ingredients.toMutableList()
        updated[index] = value
        state = state.copy(ingredients = updated)
    }

    fun addIngredient() {
        state = state.copy(ingredients = state.ingredients + "")
    }

    fun removeIngredient(index: Int) {
        state = state.copy(ingredients = state.ingredients.filterIndexed { i, _ -> i != index })
    }

    fun updateStep(index: Int, value: String) {
        val updated = state.steps.toMutableList()
        updated[index] = value
        state = state.copy(steps = updated)
    }

    fun addStep() {
        state = state.copy(steps = state.steps + "")
    }

    fun removeStep(index: Int) {
        state = state.copy(steps = state.steps.filterIndexed { i, _ -> i != index })
    }

    fun toRecipe(id: Int): Recipe {
        return Recipe(
            id = id,
            name = state.name,
            types = state.types,
            imageRes = state.imageRes,
            ingredients = state.ingredients.filter { it.isNotBlank() },
            steps = state.steps.filter { it.isNotBlank() },
        )
    }
}