package com.example.foodbot.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.foodbot.database.entities.Foodplan

@Dao
interface FoodplanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(foodplan: Foodplan)

    @Query("SELECT * FROM foodplan")
    suspend fun get(): Foodplan?
}