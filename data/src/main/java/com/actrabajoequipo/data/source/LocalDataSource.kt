package com.actrabajoequipo.data.source

import com.actrabajoequipo.domain.Recipe

interface LocalDataSource {

    suspend fun isEmpty(): Boolean
    suspend fun saveRecipes(recipes: List<Recipe>)
    suspend fun getRecipes(): List<Recipe>
    suspend fun update(recipe: Recipe)
    suspend fun findById(id: String): Recipe
    suspend fun search(query: String): List<Recipe>
}