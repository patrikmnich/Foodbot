package com.example.foodbot.ui

import android.content.Context
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
    onNextButtonClicked: (Recipe) -> Unit,
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

    Column(
        modifier = modifier
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
                detectDragGestures(onDrag = { _,_ -> focusManager.clearFocus() })
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
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = context.getString(R.string.search)
                )
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
                    onClick = { onNextButtonClicked(filteredRecipes[index]) }
                )
            }
        }
    }
}

@Preview(widthDp = 380, heightDp = 600, locale = "sk", showBackground = true)
@Composable
fun StartOrderPreview() {
    FoodbotTheme {
        RecipesScreen(
            recipes = List(10) { Recipe.example() },
            onNextButtonClicked = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}
