package com.actrabajoequipo.recipesapp.model

class RecipesRepository {

    suspend fun getRecipes() =
        RecipeBook.service
            .getRecipes()
}