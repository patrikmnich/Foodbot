package com.example.foodbot.database.entities

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodbot.database.model.FoodType
import com.google.gson.annotations.SerializedName

@Entity(tableName = Recipe.TABLE_NAME)
data class Recipe(

    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String,

    @ColumnInfo(name = "types")
    @SerializedName("types")
    val types: Set<FoodType>,

    @ColumnInfo(name = "imageResId")
    @SerializedName("imageResId")
    val imageRes: String,

    @ColumnInfo(name = "ingredients")
    @SerializedName("ingredients")
    val ingredients: List<String>, // TODO: toto treba upravit, rozdelit to na ingredienciu samotnu a mnozstvo

    @ColumnInfo(name = "steps")
    @SerializedName("steps")
    val steps: List<String>,

    // TODO: additional features like spicy, veggie..
) {
    companion object {
        const val TABLE_NAME = "RECIPES"

        private val map = mapOf(
            "Prawn tikka masala" to "prawn_tikka_masala",
            "Chipotle chicken & slaw" to "chipotle_chicken_slaw",
            "Spiced salmon & tomato traybake" to "spiced_salmon_tomato_traybake",
            "Fish & chip traybake" to "fish_chip_traybake"
        )

        fun example(): Recipe {
            val recipe = map.entries.random()
            return Recipe(0, recipe.key, setOf(FoodType.LUNCH), recipe.value,
                listOf(
                    "2 large sweet potatoes, cut into thin wedges",
                    "1 tbsp rapeseed oil",
                    "4 tbsp fat-free natural yogurt",
                    "2 tbsp low-fat mayonnaise",
                    "3 cornichons, finely chopped, plus 1 tbsp of the brine",
                    "1 shallot, finely chopped",
                    "1 tbsp finely chopped dill, plus extra to serve",
                    "300g frozen peas",
                    "50ml milk",
                    "1 tbsp finely chopped mint",
                    "4 cod or pollock loin fillets",
                    "1 lemon, cut into wedges, to serve"
                ),
                listOf(
                    "Heat the oven to 220C/200C fan/gas 8. Toss the sweet potatoes with the oil and some seasoning on a baking tray. Roast for 20 mins.",
                    "Combine the yogurt, mayonnaise, cornichons and reserved brine, the shallot and dill with 1 tbsp cold water in a small bowl and set aside.",
                    "Meanwhile, put the peas in a pan with the milk, bring to a simmer and cook for 5 mins. Blitz the mixture using a hand blender until roughly puréed. Stir in the mint and season to taste. Set aside.",
                    "Add the cod or pollock to the baking tray with the sweet potatoes, season and cook for 10-15 mins more, or until cooked through. Warm through the pea mixture. Scatter over some dill and serve the traybake with the yogurt tartare and the mushy peas."
                ))
        }
    }

    fun getResourceId(context: Context): Int =
        context.resources.getIdentifier(imageRes, "drawable", context.packageName)
}