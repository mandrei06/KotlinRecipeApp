package com.example.recipeapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.adapter.RecipeListAdapter
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.viewmodel.RecipeViewModel

class MainActivity : AppCompatActivity() {

    private val recipeViewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isFirstLaunch()) {
            populateSampleData()
            setFirstLaunchFlag()
        }

        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = RecipeListAdapter { recipe ->
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("recipe", recipe) // Pass the recipe ID
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observe recipe list changes
        recipeViewModel.allRecipes.observe(this) { recipes ->
            recipes?.let { adapter.submitList(it) }
        }

        val fabAddRecipe = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.fab_add_recipe)
        fabAddRecipe.setOnClickListener {
            val intent = Intent(this, AddEditRecipeActivity::class.java)
            startActivity(intent)
        }
    }
    private fun isFirstLaunch(): Boolean {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isFirstLaunch", true)
    }

    private fun setFirstLaunchFlag() {
        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("isFirstLaunch", false)
            apply()
        }
    }

    private fun populateSampleData() {
        val sampleRecipes = listOf(
            Recipe(name = "Sample Recipe 1", description = "Description 1", ingredients = "Ingredients 1", steps = "Steps 1", category = "vegetarian"),
            Recipe(name = "Sample Recipe 2", description = "Description 2", ingredients = "Ingredients 2", steps = "Steps 2", category = "vegetarian")
        )
        sampleRecipes.forEach { recipeViewModel.insert(it) }
    }
}
