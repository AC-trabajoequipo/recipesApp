package com.actrabajoequipo.recipesapp.server

import com.actrabajoequipo.data.source.RemoteDataSource
import com.actrabajoequipo.domain.Recipe
import com.actrabajoequipo.domain.User
import com.actrabajoequipo.recipesapp.toDomainMovie
import com.actrabajoequipo.recipesapp.toDomainUser
import com.actrabajoequipo.recipesapp.toServerMovie
import com.actrabajoequipo.recipesapp.toServerUser

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

    override suspend fun getUsers(): Map<String, User> =
        ApiBook.service
            .getUsers().mapValues {
                it.value.toDomainUser()
            }

    override suspend fun patchUser(uid: String, user: User) {
        ApiBook.service
            .patchUser(uid, user.toServerUser())
    }

    override suspend fun editUser(uid: String, newUser: User) {
        ApiBook.service
            .editUser(uid, newUser.toServerUser())
    }

    override suspend fun deleteUser(uid: String) {
        ApiBook.service
            .deleteUser(uid)
    }

    override suspend fun postUser(user: User) {
        ApiBook.service
            .postUser(user.toServerUser())
    }

    override suspend fun putUser(user: User) {
        ApiBook.service
            .putUser(user.toServerUser())
    }
}