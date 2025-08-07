package com.example.foodbot.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodbot.R
import com.example.foodbot.database.entities.Foodplan
import com.example.foodbot.database.entities.Recipe
import com.example.foodbot.ui.components.RecipeListItem
import com.example.foodbot.ui.theme.FoodbotTheme

@Composable
fun FoodplanScreen(
    foodplan: Foodplan?,
    onGenerateClicked: () -> Unit,
    onRecipeClicked: (Recipe) -> Unit,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
) {
    if (foodplan == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = onGenerateClicked) {
                Text(context.getString(R.string.generate_foodplan))
            }
        }
    } else {

        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0F,
            pageCount = { foodplan.days.size }
        )

        Column(modifier = modifier
            .padding(top = 8.dp)
            .fillMaxSize()
        ) {
            // Carousel for days
            HorizontalPager(
                state = pagerState,
                flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
                contentPadding = PaddingValues(horizontal = 16.dp),
            ) { page ->
                val selectedDay = foodplan.days[page]

                Column(
                    modifier = modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = selectedDay.day,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(selectedDay.recipes) { recipe ->
                            RecipeListItem(recipe = recipe, onClick = { onRecipeClicked(recipe) })
                        }
                    }
                }
            }
        }

        Row(
            Modifier
                .fillMaxSize()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.outlineVariant
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }

    }
}


@Preview(widthDp = 380, heightDp = 600, locale = "sk", showBackground = true)
@Composable
fun FoodPlanScreenPreview() {
    FoodbotTheme {
        FoodplanScreen(
            Foodplan.example(), {}, {}
        )
    }
}

@Preview(widthDp = 380, heightDp = 600, locale = "sk", showBackground = true)
@Composable
fun FoodPlanScreenEmptyPreview() {
    FoodbotTheme {
        FoodplanScreen(
            null, {}, {}
        )
    }
}
