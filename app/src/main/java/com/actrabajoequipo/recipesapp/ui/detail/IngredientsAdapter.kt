package com.actrabajoequipo.recipesapp.ui.detail

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.IngredientItemBinding
import com.actrabajoequipo.recipesapp.ui.inflate

class IngredientsAdapter(private val ingredients: List<String>) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.ingredient_item, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = ingredients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.bind(ingredient)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = IngredientItemBinding.bind(view)
        fun bind(ingredient: String) = with(binding) {
            name.text = ingredient
        }
    }
}