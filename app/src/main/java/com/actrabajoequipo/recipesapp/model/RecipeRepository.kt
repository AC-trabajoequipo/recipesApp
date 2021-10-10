package com.actrabajoequipo.recipesapp.model

class RecipeRepository {
    suspend fun postRecipe(recipeDto: RecipeDto) = RecipeBook.service.postRecipe(recipeDto)
}