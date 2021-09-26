package com.actrabajoequipo.recipesapp.model

import com.actrabajoequipo.recipesapp.Recipe
import retrofit2.http.GET
import retrofit2.http.POST

interface RecipeService {

    @GET("recipes.json")
    suspend fun getRecipes(): List<RecipeDto>
}