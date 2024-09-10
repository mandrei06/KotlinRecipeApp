package com.example.recipeapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.recipeapp.model.Recipe

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: Recipe)

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipe WHERE id = :id")
    fun getRecipeById(id: Int): LiveData<Recipe>

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)
}
