package com.example.recipeapp.repository

import androidx.lifecycle.LiveData
import com.example.recipeapp.dao.RecipeDao
import com.example.recipeapp.database.RecipeDatabase
import com.example.recipeapp.model.Recipe

class RecipeRepository(private val recipeDao: RecipeDao) {

    // Function to get all recipes
    fun getAllRecipes(): LiveData<List<Recipe>> = recipeDao.getAllRecipes()

    // Function to insert a new recipe
    suspend fun insertRecipe(recipe: Recipe) {
        recipeDao.insertRecipe(recipe)
    }

    // Function to update an existing recipe
    suspend fun updateRecipe(recipe: Recipe) {
        recipeDao.updateRecipe(recipe)
    }

    // Function to delete a recipe
    suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }
}
