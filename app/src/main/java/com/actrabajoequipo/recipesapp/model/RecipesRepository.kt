package com.actrabajoequipo.recipesapp.model

import com.actrabajoequipo.recipesapp.RecipesApp
import com.actrabajoequipo.recipesapp.model.database.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipesRepository(application: RecipesApp) {

    private val db = application.db

    suspend fun getRecipes(): List<Recipe> = withContext(Dispatchers.IO) {
        with(db.recipeDao()) {
            if (recipeCount() <= 0) {
                val recipes = ApiBook.SERVICE
                    .getRecipes()

                insertRecipes(recipes.map { it.convertToDbRecipe() })
            }

            getAll()
        }
    }

    suspend fun findById(id: String): Recipe = withContext(Dispatchers.IO) {
        db.recipeDao().findById(id)
    }

    suspend fun update(recipe: Recipe) = withContext(Dispatchers.IO) {
        db.recipeDao().updateRecipe(recipe)
    }

}

private fun RecipeDto.convertToDbRecipe() = Recipe(
    id,
    name,
    description ?: "",
    imgUrl ?: "",
    ingredients,
    preparation ?: "",
    false
)