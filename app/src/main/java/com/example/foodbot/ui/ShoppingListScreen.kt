package com.example.foodbot.ui

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.foodbot.R
import com.example.foodbot.database.entities.Foodplan
import com.example.foodbot.ui.theme.FoodbotTheme

@Composable
fun ShoppingListScreen(
    foodplan: Foodplan?,
    onGenerateClicked: () -> Unit,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
) {

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (foodplan == null) {
                Text(context.getString(R.string.oops))
                Text(context.getString(R.string.no_foodplan_yet))
                Text(context.getString(R.string.please_generate_foodplan))
            } else {
                Button(onClick = onGenerateClicked) {
                    Text(context.getString(R.string.generate_shopping_list))
                }
            }
        }
    }
}


@Preview(widthDp = 380, heightDp = 600, locale = "sk", showBackground = true)
@Composable
fun ShoppingListNoFoodplanScreenPreview() {
    FoodbotTheme {
        ShoppingListScreen(null, {})
    }
}

@Preview(widthDp = 380, heightDp = 600, locale = "sk", showBackground = true)
@Composable
fun ShoppingListGenerateScreenPreview() {
    FoodbotTheme {
        ShoppingListScreen(Foodplan.example(), {})
    }
}
