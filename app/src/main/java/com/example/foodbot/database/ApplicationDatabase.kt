package com.example.foodbot.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodbot.database.converters.Converters
import com.example.foodbot.database.dao.FoodplanDao
import com.example.foodbot.database.dao.RecipesDao
import com.example.foodbot.database.entities.Foodplan
import com.example.foodbot.database.entities.Recipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

private const val DB_NAME = "AppDatabase.db"

@Database(
    entities = [
        Recipe::class,
        Foodplan::class
    ],
    exportSchema = false,
    version = 1
)

@TypeConverters(Converters::class)
abstract class ApplicationDatabase: RoomDatabase() {

    abstract fun recipesDao(): RecipesDao
    abstract fun foodplanDao(): FoodplanDao
}

@Module
@InstallIn(SingletonComponent::class)
object ApplicationDatabaseModule {

    private val gson = Gson()

    private fun loadJsonFromFile(context: Context, filename: String): String {
        return context.assets.open(filename).bufferedReader().use { it.readText() }
    }

    private inline fun <reified T> parseRecipes(jsonString: String): List<T> {
        val listType = object : TypeToken<List<T>>() {}.type
        return gson.fromJson(jsonString, listType)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ApplicationDatabase {
        val dbFile = context.getDatabasePath(DB_NAME)
        val builder = databaseBuilder(
            context,
            ApplicationDatabase::class.java,
            DB_NAME
        )

        val db = builder.build()
            .apply {
                // insert default recipes if db is created for the first time
                if (!dbFile.exists()) {
                    val recipes = parseRecipes<Recipe>(loadJsonFromFile(context, "recipes.json"))

                    CoroutineScope(Dispatchers.IO).launch {
                        recipesDao().insertAll(recipes)
                    }
                }
        }

        return db
    }

    @Provides
    fun provideRecipesDao(database: ApplicationDatabase): RecipesDao = database.recipesDao()

    @Provides
    fun provideFoodplanDao(database: ApplicationDatabase): FoodplanDao = database.foodplanDao()

}