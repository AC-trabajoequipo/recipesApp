package com.actrabajoequipo.recipesapp.server

import com.actrabajoequipo.data.source.RemoteDataSource
import com.actrabajoequipo.domain.Recipe
import com.actrabajoequipo.recipesapp.toDomainMovie
import com.actrabajoequipo.recipesapp.toServerMovie

class RecipesDataSource : RemoteDataSource {
    override suspend fun getRecipes(): List<Recipe> =
        ApiBook.service
            .getRecipes().values
            .map {
                it.toDomainMovie()
            }

    override suspend fun postRecipe(recipe: Recipe) {
        ApiBook.service
            .postRecipe(recipe.toServerMovie())
    }
}