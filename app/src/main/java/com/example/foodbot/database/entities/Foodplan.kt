package com.example.foodbot.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.foodbot.database.model.FoodplanDay
import com.google.gson.annotations.SerializedName

@Entity(tableName = Foodplan.TABLE_NAME)
data class Foodplan(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @ColumnInfo(name = "foodplan_days")
    @SerializedName("foodplan_days")
    val days: List<FoodplanDay>,

    )  {
    companion object {
        const val TABLE_NAME = "FOODPLAN"

        fun example() = Foodplan(
            id = 0,
            days = List(7) { FoodplanDay.example() }
        )
    }
}