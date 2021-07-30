package com.actrabajoequipo.recipesapp.ui.home.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.RecipeItemBinding
import com.actrabajoequipo.recipesapp.model.Recipe
import com.actrabajoequipo.recipesapp.ui.basicDiffUtil
import com.actrabajoequipo.recipesapp.ui.inflate
import com.actrabajoequipo.recipesapp.ui.loadUrl

class RecipesAdapter(private val listener: (Recipe) -> Unit) :
    RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    var recipes: List<Recipe> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.recipe_item, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
        holder.itemView.setOnClickListener { listener(recipe) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = RecipeItemBinding.bind(view)
        fun bind(recipe: Recipe) = with(binding) {
            recipe.imageUrl?.let { image.loadUrl(it) }
            name.text = recipe.name
        }
    }
}