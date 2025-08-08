package com.example.foodbot.ui

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.foodbot.R
import com.example.foodbot.database.entities.Recipe
import com.example.foodbot.ui.theme.FoodbotTheme

@Composable
fun NewRecipeScreen(
    onAddButtonClicked: (Recipe) -> Unit,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "New recipe",
            textAlign = TextAlign.Center
        )
    }
}

@Preview(widthDp = 380, heightDp = 630, locale = "sk", showBackground = true)
@Composable
fun NewRecipePreview() {
    FoodbotTheme {
        NewRecipeScreen(
            onAddButtonClicked = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}