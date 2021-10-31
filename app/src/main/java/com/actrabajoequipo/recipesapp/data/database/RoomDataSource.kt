package com.actrabajoequipo.recipesapp.data.database

import com.actrabajoequipo.data.source.LocalDataSource
import com.actrabajoequipo.domain.Recipe
import com.actrabajoequipo.recipesapp.toDomainMovie
import com.actrabajoequipo.recipesapp.toRoomMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(db: RecipeDatabase) : LocalDataSource {

    private val recipesDao = db.recipeDao()

    override suspend fun isEmpty(): Boolean =
        withContext(Dispatchers.IO) { recipesDao.recipeCount() <= 0 }

    override suspend fun saveRecipes(recipes: List<Recipe>) =
        withContext(Dispatchers.IO) {
            recipesDao.insertRecipes(recipes.map { it.toRoomMovie() })
        }

    override suspend fun getRecipes(): List<Recipe> =
        withContext(Dispatchers.IO) {
            recipesDao.getAll().map { it.toDomainMovie() }
        }

    override suspend fun update(recipe: Recipe) = withContext(Dispatchers.IO) {
        recipesDao.updateRecipe(recipe.toRoomMovie())
    }

    override suspend fun findById(id: String): Recipe =
        recipesDao.findById(id).toDomainMovie()

    override suspend fun search(query: String): List<Recipe> {
        return if (query.isBlank()) {
            emptyList()
        } else {
            recipesDao.getAll().filter {
                val regex = query.toRegex(RegexOption.IGNORE_CASE)
                regex.containsMatchIn(it.name)
            }.map {
                it.toDomainMovie()
            }
        }
    }
}