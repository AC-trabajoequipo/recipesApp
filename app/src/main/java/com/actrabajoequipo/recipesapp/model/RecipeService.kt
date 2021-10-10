package com.actrabajoequipo.recipesapp.model

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RecipeService {

    @GET("recipes.json")
    suspend fun getRecipes(): List<RecipeDto>

    @POST("recipes.json")
    suspend fun postRecipe(@Body recipeDto: RecipeDto) : PostRecipeDto
}