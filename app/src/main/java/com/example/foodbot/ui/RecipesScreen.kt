package com.example.foodbot.ui

import android.content.Context
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodbot.R
import com.example.foodbot.database.entities.Recipe
import com.example.foodbot.ui.components.RecipeListItem
import com.example.foodbot.ui.theme.FoodbotTheme

@Composable
fun RecipesScreen(
    recipes: List<Recipe>,
    onRecipeClicked: (Recipe) -> Unit,
    onFabClicked: () -> Unit,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()

    var searchQuery by remember { mutableStateOf("") }

    val filteredRecipes = recipes.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    // Hide keyboard on scroll
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemScrollOffset }
            .collect {
                focusManager.clearFocus()
                keyboardController?.hide()
            }
    }

    Box {
        Column(
            modifier = modifier
                .padding(8.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = { focusManager.clearFocus() })
                    detectDragGestures(onDrag = { _, _ -> focusManager.clearFocus() })
                },
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text(context.getString(R.string.search_recipe)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true,
                trailingIcon = {
                    if (searchQuery.isBlank())
                        return@OutlinedTextField

                    IconButton(
                        onClick = { searchQuery = "" }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = context.getString(R.string.search)
                        )
                    }
                }
            )

            LazyColumn(
                state = listState,
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(filteredRecipes.size) { index ->
                    RecipeListItem(
                        recipe = filteredRecipes[index],
                        onClick = { onRecipeClicked(filteredRecipes[index]) }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }

        FloatingActionButton(
            onClick = { onFabClicked() },
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Recipe")
        }
    }
}

@Preview(widthDp = 380, heightDp = 630, locale = "sk", showBackground = true)
@Composable
fun RecipesPreview() {
    FoodbotTheme {
        RecipesScreen(
            recipes = List(10) { Recipe.example() },
            onRecipeClicked = {},
            onFabClicked = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}
