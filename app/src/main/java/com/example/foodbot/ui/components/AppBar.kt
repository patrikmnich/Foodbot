package com.example.foodbot.ui.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodbot.LocalAppLanguage
import com.example.foodbot.R
import com.example.foodbot.ui.navigation.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodbotAppBar(
    currentScreen: Destination,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    onToggleLanguage: () -> Unit = {},
) {
    TopAppBar(
        title = { Text(context.getString(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        scrollBehavior = scrollBehavior,
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = context.getString(R.string.back_button)
                    )
                }
            }
        },
        actions =  {
            Text(
                text = LocalAppLanguage.current.uppercase(),
                modifier = Modifier
                    .clickable(onClick = onToggleLanguage)
                    .padding(16.dp)
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(widthDp = 380, heightDp = 600, locale = "sk", showBackground = true)
@Composable
fun AppBarPreview() {
    Column {
        FoodbotAppBar(
            Destination.Foodplan,
            true,
            {},
            TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState()),
        )
        FoodbotAppBar(
            Destination.Foodplan,
            false,
            {},
            TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
        )
    }
}