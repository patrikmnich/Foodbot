package com.example.foodbot.database.model

import android.content.Context
import com.example.foodbot.R

enum class FoodType {
    BREAKFAST,
    MORNING_SNACK,
    LUNCH,
    AFTERNOON_SNACK,
    DINNER;

    fun getString(context: Context): String {
        return when (this) {
            BREAKFAST -> context.getString(R.string.breakfast)
            MORNING_SNACK -> context.getString(R.string.morning_snack)
            LUNCH -> context.getString(R.string.lunch)
            AFTERNOON_SNACK -> context.getString(R.string.afternoon_snack)
            DINNER -> context.getString(R.string.dinner)
        }
    }
}