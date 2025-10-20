package com.example.foodbot.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.foodbot.database.dao.FoodplanDao
import com.example.foodbot.database.dao.RecipesDao
import com.example.foodbot.database.entities.Foodplan
import com.example.foodbot.database.entities.Recipe
import com.example.foodbot.database.model.FoodplanDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * [AppViewModel] holds information about a foodplan.
 */
@HiltViewModel
class AppViewModel @Inject constructor(
    val recipesDao: RecipesDao,
    val foodplanDao: FoodplanDao,
) : ViewModel() {

    companion object {
        private val TAG = AppViewModel::class.simpleName.toString()
    }

    private val _foodplanState: MutableStateFlow<Foodplan?> = MutableStateFlow(null)
    val foodplanState: StateFlow<Foodplan?> = _foodplanState.asStateFlow()

    private val _selectedFoodplanRecipeState: MutableStateFlow<Recipe?> = MutableStateFlow(null)
    val selectedFoodplanRecipeState: StateFlow<Recipe?> = _selectedFoodplanRecipeState.asStateFlow()

    private val _selectedSearchRecipeState: MutableStateFlow<Recipe?> = MutableStateFlow(null)
    val selectedSearchRecipeState: StateFlow<Recipe?> = _selectedSearchRecipeState.asStateFlow()

    suspend fun getAllRecipes(): List<Recipe> = recipesDao.getAll()

    fun setSelectedFoodplanRecipe(recipe: Recipe) {
        _selectedFoodplanRecipeState.value = recipe
    }

    fun setSelectedSearchRecipe(recipe: Recipe) {
        _selectedSearchRecipeState.value = recipe
    }

    suspend fun fetchFoodplan() {
        _foodplanState.value = foodplanDao.get()
    }

    suspend fun checkFoodplanExpiration() {
        _foodplanState.value?.let { foodplan ->

        }
    }

    suspend fun generateFoodplan(context: Context) {
        Log.e(TAG, "Generating foodplan")
        val recipes = getAllRecipes()

        val foodplayDays = mutableListOf<FoodplanDay>()
        for (i in 0 until 7) {
            foodplayDays.add(
                FoodplanDay(
                    i,
                    listOf(
                        recipes.random(),
                        recipes.random(),
                        recipes.random(),
                        recipes.random(),
                        recipes.random()
                    ) // TODO: pridat realne recepty a generovanie
                )
            )
        }

        val timestamp = System.currentTimeMillis()

        val foodplan = Foodplan(0, foodplayDays, timestamp)
        foodplanDao.insert(foodplan)
        _foodplanState.value = foodplan
    }
}