package com.actrabajoequipo.recipesapp

import com.actrabajoequipo.data.source.LocalDataSource
import com.actrabajoequipo.data.source.RemoteDataSource
import com.actrabajoequipo.domain.Recipe
import com.actrabajoequipo.domain.User

val mockedRecipe = Recipe(
    "0",
    "1",
    "Cocido madrileño",
    "Receta de cocido madrileño, un guiso de garbanzos con verduras, carne, tocino y algunos embutidos. El cocido madrileño es uno de los platos más típicos de Madrid.",
    "https://www.hogarmania.com/archivos/201410/5432-1-cocido-madrileno-917-xl-668x400x80xX.jpg",
    listOf("300 g de garbanzos", "1 muslo de pollo", "1 morcilla de cebolla"),
    "",
    false
)

val defaultFakeRecipes = listOf(
    mockedRecipe.copy("1"),
    mockedRecipe.copy("2"),
    mockedRecipe.copy("3"),
    mockedRecipe.copy("4")
)

class FakeLocalDataSource : LocalDataSource {

    var recipes: List<Recipe> = emptyList()

    override suspend fun isEmpty(): Boolean {
        return recipes.isEmpty()
    }
    override suspend fun saveRecipes(recipes: List<Recipe>) {
        this.recipes = recipes
    }

    override suspend fun getRecipes(): List<Recipe> = recipes

    override suspend fun update(recipe: Recipe) {
        recipes = recipes.filterNot { it.id == recipe.id } + recipe
    }

    override suspend fun findById(id: String): Recipe = recipes.first { it.id == id }

    override suspend fun search(query: String): List<Recipe> =
        recipes.filter { it.name.contains(query) }

    override suspend fun findRecipeByUserUID(userId: String): List<Recipe> =
        recipes.filter { it.idUser == userId }

    override suspend fun findRecipeByFavourites(isFav: Boolean): List<Recipe> {
        TODO("Not yet implemented")
    }
}

class FakeRemoteDataSource : RemoteDataSource {

    var recipes = defaultFakeRecipes

    override suspend fun getRecipes(): List<Recipe> = recipes

    override suspend fun postRecipe(recipe: Recipe): String? {
        TODO("Not yet implemented")
    }

    override suspend fun getUsers(): Map<String, User> {
        TODO("Not yet implemented")
    }

    override suspend fun patchUser(uid: String, user: User): String? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(uid: String) {
        TODO("Not yet implemented")
    }

    override suspend fun findUserById(uid: String): User {
        TODO("Not yet implemented")
    }
}