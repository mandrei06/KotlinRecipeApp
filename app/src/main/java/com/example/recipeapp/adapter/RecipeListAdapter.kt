package com.example.recipeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.model.Recipe

class RecipeListAdapter(private val onClick: (Recipe) -> Unit) :
    RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>() {

    private var recipes: List<Recipe> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe, onClick)
    }

    override fun getItemCount(): Int = recipes.size

    fun submitList(newRecipes: List<Recipe>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recipeName: TextView = itemView.findViewById(R.id.recipeName)

        fun bind(recipe: Recipe, onClick: (Recipe) -> Unit) {
            recipeName.text = recipe.name
            itemView.setOnClickListener { onClick(recipe) }
        }
    }
}
