package com.actrabajoequipo.data.source

import com.actrabajoequipo.domain.Recipe

interface RemoteDataSource {
    suspend fun getRecipes(): List<Recipe>
    suspend fun postRecipe(recipe: Recipe)
}