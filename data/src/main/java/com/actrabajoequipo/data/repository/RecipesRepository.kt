package com.actrabajoequipo.data.repository

import com.actrabajoequipo.data.source.LocalDataSource
import com.actrabajoequipo.data.source.RemoteDataSource
import com.actrabajoequipo.domain.Recipe

class RecipesRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getRecipes(): List<Recipe> {
        if (localDataSource.isEmpty()) {
            val recipes = remoteDataSource.getRecipes()
            localDataSource.saveRecipes(recipes)
        }
        return localDataSource.getRecipes()
    }

    suspend fun findById(id: String): Recipe = localDataSource.findById(id)

    suspend fun update(recipe: Recipe) = localDataSource.update(recipe)

    suspend fun search(query: String) = localDataSource.search(query)

    suspend fun postRecipe(recipe: Recipe) = remoteDataSource.postRecipe(recipe)
}