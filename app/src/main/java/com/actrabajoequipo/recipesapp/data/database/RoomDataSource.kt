package com.actrabajoequipo.recipesapp.data.database

import com.actrabajoequipo.data.source.LocalDataSource
import com.actrabajoequipo.domain.Recipe
import com.actrabajoequipo.recipesapp.toDomainRecipe
import com.actrabajoequipo.recipesapp.toRoomRecipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(db: RecipeDatabase) : LocalDataSource {

    private val recipesDao = db.recipeDao()

    override suspend fun isEmpty(): Boolean =
        withContext(Dispatchers.IO) { recipesDao.recipeCount() <= 0 }

    override suspend fun saveRecipes(recipes: List<Recipe>) =
        withContext(Dispatchers.IO) {
            recipesDao.insertRecipes(recipes.map { it.toRoomRecipe() })
        }

    override suspend fun getRecipes(): List<Recipe> =
        withContext(Dispatchers.IO) {
            recipesDao.getAll().map { it.toDomainRecipe() }
        }

    override suspend fun update(recipe: Recipe) = withContext(Dispatchers.IO) {
        recipesDao.updateRecipe(recipe.toRoomRecipe())
    }

    override suspend fun findById(id: String): Recipe = withContext(Dispatchers.IO) {
    recipesDao.findById(id).toDomainRecipe()
    }

    override suspend fun search(query: String): List<Recipe> {
        return if (query.isBlank()) {
            emptyList()
        } else {
            recipesDao.getAll().filter {
                val regex = query.toRegex(RegexOption.IGNORE_CASE)
                regex.containsMatchIn(it.name)
            }.map {
                it.toDomainRecipe()
            }
        }
    }

    override suspend fun findRecipeByUserUID(userId: String): List<Recipe> =
        withContext(Dispatchers.IO) {
            recipesDao.findRecipeByUserUID(userId).map { it.toDomainRecipe() }
        }

    override suspend fun findRecipeByFavourites(isFav: Boolean): List<Recipe> =
        withContext(Dispatchers.IO) {
            recipesDao.findRecipeByFavourites(isFav).map { it.toDomainRecipe() }
        }
}