package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.viewmodel.RecipeViewModel

class AddEditRecipeActivity : AppCompatActivity() {

    private val recipeViewModel: RecipeViewModel by viewModels()
    private var recipe: Recipe? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_recipe)

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editDescription = findViewById<EditText>(R.id.editTextDescription)
        val editTextIngredients = findViewById<EditText>(R.id.editTextIngredients)
        val editTextSteps = findViewById<EditText>(R.id.editTextSteps)
        val spinnerCategory = findViewById<Spinner>(R.id.spinnerCategory)
        val buttonSave = findViewById<Button>(R.id.buttonSave)

        // Get the recipe from the intent if it exists
        recipe = intent.getParcelableExtra("recipe")
        recipe?.let {
            editTextName.setText(it.name)
            editDescription.setText(it.description)
            editTextIngredients.setText(it.ingredients)
            editTextSteps.setText(it.steps)

            // Set the selected category in the spinner
            val categoryPosition = resources.getStringArray(R.array.recipe_categories).indexOf(it.category)
            spinnerCategory.setSelection(categoryPosition)

            buttonSave.text = "Update Recipe"
        }

        buttonSave.setOnClickListener {
            val name = editTextName.text.toString()
            val description = editDescription.text.toString()
            val ingredients = editTextIngredients.text.toString()
            val steps = editTextSteps.text.toString()
            val category = spinnerCategory.selectedItem.toString()

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(ingredients) || TextUtils.isEmpty(steps)) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val newRecipe = recipe?.copy(name = name, description = description, ingredients = ingredients, steps = steps, category = category)
                    ?: Recipe(name = name, description = "Sample description", ingredients = ingredients, steps = steps, category = category)

                if (recipe != null) {
                    recipeViewModel.update(newRecipe)
                } else {
                    recipeViewModel.insert(newRecipe)
                }

                val resultIntent = Intent().apply {
                    putExtra("updated_recipe", newRecipe)
                }
                setResult(RESULT_OK, resultIntent)

                finish()
            }
        }
    }
}

