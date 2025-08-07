package com.example.foodbot.ui.components

import android.content.Context
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.foodbot.ui.navigation.Destination

@Composable
fun FoodbotNavigationBar(
    currentDestination: Destination,
    context: Context,
    onItemSelected: (Destination) -> Unit
) {
    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
        Destination.entries.filter { it.icon != null }
            .forEach { destination ->
                NavigationBarItem(
                    selected = currentDestination == destination,
                    onClick = { onItemSelected(destination) },
                    icon = {
                        Icon(destination.icon!!, contentDescription = null)
                    },
                    label = { Text(context.getString(destination.title)) }
                )
            }
    }
}