package com.actrabajoequipo.recipesapp.model

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RecipeService {

    @GET("recipes.json")
    suspend fun getRecipes(): Map<String,RecipeDto>

    @POST("recipes.json")
    suspend fun postRecipe(@Body recipeDto: RecipeDto) : PostRecipeDto
}