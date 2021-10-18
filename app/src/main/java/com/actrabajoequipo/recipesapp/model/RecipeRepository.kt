package com.actrabajoequipo.recipesapp.model

class RecipeRepository {

    suspend fun postRecipe(recipeDto: RecipeDto) = ApiBook.SERVICE.postRecipe(recipeDto)
}