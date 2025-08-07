package com.example.foodbot.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodbot.R
import com.example.foodbot.database.entities.Recipe
import com.example.foodbot.ui.theme.FoodbotTheme
import kotlinx.coroutines.launch

@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
) {
    val imageResId = recipe.getResourceId(context)

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()

    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = recipe.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val tabs = listOf(context.getString(R.string.ingredients), context.getString(R.string.steps))

            val pagerState = androidx.compose.foundation.pager.rememberPagerState(
                initialPage = 0,
                initialPageOffsetFraction = 0F,
                pageCount = { tabs.size }
            )

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.fillMaxWidth()
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                        text = { Text(title) }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
                modifier = Modifier.weight(1f)
            ) { page ->
                when (page) {
                    0 -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(8.dp)
                        ) {
                            recipe.ingredients.forEach { ingredient ->
                                Text(text = "• $ingredient", modifier = Modifier.padding(vertical = 4.dp))
                            }
                        }
                    }
                    1 -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                                .padding(8.dp)
                        ) {
                            recipe.steps.forEachIndexed { index, step ->
                                Text(
                                    text = "${index + 1}. $step",
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(widthDp = 380, heightDp = 600, locale = "sk", showBackground = true)
@Composable
fun SelectOptionPreview() {
    FoodbotTheme {
        RecipeDetailScreen(
            recipe = Recipe.example(),
            modifier = Modifier.fillMaxHeight()
        )
    }
}
