package com.example.foodbot.ui

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodbot.R
import com.example.foodbot.data.NewRecipeState
import com.example.foodbot.database.entities.Recipe
import com.example.foodbot.database.model.FoodType
import com.example.foodbot.ui.theme.FoodbotTheme
import com.example.foodbot.viewmodels.NewRecipeViewModel

@Composable
fun AddRecipeScreen(
    viewModel: NewRecipeViewModel?, // nullable for preview purposes..
    onAddButtonClicked: (Recipe) -> Unit,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
) {
    val state = viewModel?.state ?: remember { NewRecipeState() }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {

        Box(
            modifier = Modifier
                .height(128.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.LightGray.copy(alpha = 0.3f))
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Photo",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    context.getString(R.string.add_photo),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = state.name,
            onValueChange = { viewModel?.updateName(it) },
            label = { Text(context.getString(R.string.recipe_name)) },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Column {
            Text(
                text = "Select Food type",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            FoodType.entries.forEach { type ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    Checkbox(
                        checked = state.types.contains(type),
                        onCheckedChange = { checked ->
                            if (checked) viewModel?.addType(type)
                            else viewModel?.removeType(type)
                        },
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = type.getString(context),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = context.getString(R.string.ingredients),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        state.ingredients.forEachIndexed { i, ingredient ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = ingredient,
                    onValueChange = { viewModel?.updateIngredient(i, it) },
                    label = { Text("Ingredient ${i + 1}") },
                    modifier = Modifier.weight(1f),
                    trailingIcon = {
                        IconButton(onClick = { viewModel?.removeIngredient(i) }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Remove ingredient",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )

            }
        }

        Spacer(Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            HorizontalDivider(thickness = 0.5.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable { viewModel?.addIngredient() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Ingredient",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = context.getString(R.string.add_more),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp)
                        .clickable { viewModel?.addIngredient() },
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                    )

            }
            HorizontalDivider(thickness = 0.5.dp)
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = context.getString(R.string.steps),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        state.steps.forEachIndexed { i, step ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = step,
                    onValueChange = { viewModel?.updateStep(i, it) },
                    label = { Text("Step ${i + 1}") },
                    modifier = Modifier.weight(1f),
                    trailingIcon = {
                        IconButton(onClick = { viewModel?.removeStep(i) }) {
                            Icon(
                                Icons.Default.Delete,
                                contentDescription = "Remove ingredient",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            HorizontalDivider(thickness = 0.5.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp),
            ) {

                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable { viewModel?.addStep() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Step",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = context.getString(R.string.add_more),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp)
                        .clickable { viewModel?.addStep() },
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

            }
            HorizontalDivider(thickness = 0.5.dp)
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save Recipe")
        }
    }

}

@Preview(widthDp = 380, heightDp = 930, locale = "sk", showBackground = true)
@Composable
fun NewRecipePreview() {
    FoodbotTheme {
        AddRecipeScreen(
            viewModel = null,
            onAddButtonClicked = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}