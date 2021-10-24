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
                val recipes = ApiBook.service
                    .getRecipes()
                val list: List<RecipeDto> = ArrayList<RecipeDto>(recipes.values)

                var i = 0
                recipes.keys.forEach { key ->
                    list[i].id = key
                    i++
                }

                insertRecipes(list.map { it.convertToDbRecipe() })
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

    suspend fun search(query: String): List<Recipe> {
        return if (query.isBlank()) {
            emptyList()
        } else {
            withContext(Dispatchers.IO) {
                db.recipeDao().getAll().filter {
                    val regex = query.toRegex(RegexOption.IGNORE_CASE)
                    regex.containsMatchIn(it.name)
                }
            }
        }
    }

    suspend fun postRecipe(recipeDto: RecipeDto) = ApiBook.service.postRecipe(recipeDto)

}

private fun RecipeDto.convertToDbRecipe() = Recipe(
    id ?: "",
    idUser,
    name,
    description ?: "",
    imgUrl ?: "",
    ingredients,
    preparation ?: "",
    false
)