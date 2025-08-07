package com.example.foodbot

import android.util.Log
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.foodbot.database.entities.Recipe
import com.example.foodbot.ui.AppViewModel
import com.example.foodbot.ui.FoodplanScreen
import com.example.foodbot.ui.RecipeDetailScreen
import com.example.foodbot.ui.RecipesScreen
import com.example.foodbot.ui.ShoppingListScreen
import com.example.foodbot.ui.components.FoodbotAppBar
import com.example.foodbot.ui.components.FoodbotNavigationBar
import com.example.foodbot.ui.navigation.Destination
import com.example.foodbot.ui.navigation.topLevelDestinations
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodbotApp(
    currentLanguage: String,
    onToggleLanguage: (String) -> Unit,
    navController: NavHostController = rememberNavController(),
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = Destination.valueOf(
        backStackEntry?.destination?.route ?: Destination.Foodplan.name
    )

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val localizedContext = LocalContext.current.getLocalizedContext(currentLanguage)

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            FoodbotAppBar(
                currentScreen = currentScreen,
                canNavigateBack = !topLevelDestinations.contains(currentScreen),
                navigateUp = { navController.navigateUp() },
                scrollBehavior = scrollBehavior,
                context = localizedContext,
                onToggleLanguage = {
                    val language = if (currentLanguage == "en") "sk" else "en"
                    Log.e("Home", "Toggle language clicked: $language")
                    onToggleLanguage(language)
                }
            )
        },
        bottomBar = {
            FoodbotNavigationBar(
                currentDestination = currentScreen,
                context = localizedContext,
                onItemSelected = { destination ->
                    Log.e("Home", "$destination clicked, navigate to ${destination.name} from $currentScreen")

                    val isReselectingCurrentTab = navController.currentBackStackEntry
                        ?.destination
                        ?.hierarchy
                        ?.any {
                            it.navigatorName == "navigation" && (it as NavGraph).startDestinationRoute == destination.name
                        } == true

                    if (isReselectingCurrentTab) {
                        if (currentScreen == destination) {
                            Log.d("Home", "Already here, do nothing.")
                            return@FoodbotNavigationBar
                        }
                        navController.navigate(destination.name) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = false
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    } else {
                        navController.navigate(destination.name) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        val viewModel: AppViewModel = hiltViewModel()
        val foodplan by viewModel.foodplanState.collectAsState()
        val foodplanRecipe = viewModel.selectedFoodplanRecipeState.collectAsState()
        val searchRecipe = viewModel.selectedSearchRecipeState.collectAsState()

        var recipes: List<Recipe> by remember { mutableStateOf(emptyList()) }

        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            recipes = viewModel.getAllRecipes()

            viewModel.getFoodplan()
            Log.e("HOME", "Foodplan: $foodplan")
        }

        NavHost(
            navController = navController,
            startDestination = Destination.Foodplan.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = Destination.Foodplan.name) {
                FoodplanScreen(
                    foodplan = foodplan,
                    context = localizedContext,
                    onGenerateClicked = {
                        coroutineScope.launch {
                            viewModel.generateFoodplan(localizedContext)
                        }
                    },
                    onRecipeClicked = { recipe ->
                        viewModel.setSelectedFoodplanRecipe(recipe)
                        navController.navigate(Destination.RecipeDetailFoodplan.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            composable(route = Destination.RecipeDetailFoodplan.name) {
                RecipeDetailScreen(
                    recipe = foodplanRecipe.value!!,
                    modifier = Modifier.fillMaxHeight(),
                    context = localizedContext,
                )
            }

            composable(route = Destination.Recipes.name) {
                RecipesScreen(
                    recipes = recipes,
                    onNextButtonClicked = { recipe ->
                        viewModel.setSelectedSearchRecipe(recipe)
                        navController.navigate(Destination.RecipeDetailSearch.name)
                    },
                    modifier = Modifier
                        .fillMaxSize(),
                    context = localizedContext,
                )
            }
            composable(route = Destination.RecipeDetailSearch.name) {
                RecipeDetailScreen(
                    recipe = searchRecipe.value!!,
                    modifier = Modifier.fillMaxHeight(),
                    context = localizedContext,
                )
            }

            composable(route = Destination.ShoppingList.name) {
                ShoppingListScreen(
                    foodplan = foodplan,
                    onGenerateClicked = {
                        coroutineScope.launch {
                            // TODO: generate shopping list
                        }
                    },
                    context = localizedContext,
                )
            }
        }
    }
}
