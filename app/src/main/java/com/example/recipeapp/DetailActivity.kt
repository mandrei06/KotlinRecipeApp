package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.recipeapp.model.Recipe

class DetailActivity : AppCompatActivity() {

    private lateinit var recipe: Recipe
    private val EDIT_RECIPE_REQUEST = 1  // Request code for editing the recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        recipe = intent.getParcelableExtra("recipe") ?: return

        findViewById<TextView>(R.id.textViewName).text = recipe.name
        findViewById<TextView>(R.id.textViewDescription).text = recipe.description
        findViewById<TextView>(R.id.textViewIngredients).text = recipe.ingredients
        findViewById<TextView>(R.id.textViewSteps).text = recipe.steps
        findViewById<TextView>(R.id.textViewCategory).text = recipe.category

        findViewById<Button>(R.id.buttonEdit).setOnClickListener {
            val intent = Intent(this, AddEditRecipeActivity::class.java).apply {
                putExtra("recipe", recipe)
            }
            startActivity(intent)
        }

        findViewById<Button>(R.id.buttonBack).setOnClickListener {
            Log.d("DetailActivity", "Back button clicked")
            finish()
        }
    }

    // Handle the result from AddEditRecipeActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_RECIPE_REQUEST && resultCode == RESULT_OK) {
            val updatedRecipe = data?.getParcelableExtra<Recipe>("updated_recipe")
            updatedRecipe?.let {
                recipe = it  // Update the current recipe with the new data
                updateUIWithRecipe(recipe)  // Refresh the UI with the updated recipe
            }
        }
    }

    // Function to update the UI with the recipe data
    private fun updateUIWithRecipe(recipe: Recipe) {
        findViewById<TextView>(R.id.textViewName).text = recipe.name
        findViewById<TextView>(R.id.textViewDescription).text = recipe.description
        findViewById<TextView>(R.id.textViewIngredients).text = recipe.ingredients
        findViewById<TextView>(R.id.textViewSteps).text = recipe.steps
        findViewById<TextView>(R.id.textViewCategory).text = recipe.category
    }
}
