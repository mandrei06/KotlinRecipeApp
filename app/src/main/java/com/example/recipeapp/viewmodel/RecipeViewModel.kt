package com.example.recipeapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.dao.RecipeDao
import com.example.recipeapp.database.RecipeDatabase
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.repository.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel(application: Application) : AndroidViewModel(application) {
    private val recipeDao: RecipeDao = RecipeDatabase.getDatabase(application).recipeDao()
    val allRecipes: LiveData<List<Recipe>> = recipeDao.getAllRecipes()

    fun insert(recipe: Recipe) = viewModelScope.launch {
        recipeDao.insertRecipe(recipe)
    }

    fun update(recipe: Recipe) = viewModelScope.launch {
        recipeDao.updateRecipe(recipe) // Make sure you have this method in DAO
    }

    fun getRecipe(id: Int): LiveData<Recipe> {
        return recipeDao.getRecipeById(id) // Make sure you have this method in DAO
    }
}
