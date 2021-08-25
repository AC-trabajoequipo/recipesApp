package com.actrabajoequipo.recipesapp.model

import retrofit2.http.GET

interface RecipeService {

    @GET("recipes.json")
    suspend fun getRecipes(): List<RecipeDto>
}