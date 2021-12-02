package com.actrabajoequipo.recipesapp.server

import com.actrabajoequipo.data.source.RemoteDataSource
import com.actrabajoequipo.domain.Recipe
import com.actrabajoequipo.domain.User
import com.actrabajoequipo.recipesapp.toDomainRecipe
import com.actrabajoequipo.recipesapp.toDomainUser
import com.actrabajoequipo.recipesapp.toServerRecipe
import com.actrabajoequipo.recipesapp.toServerUser

class ServerDataSource : RemoteDataSource {
    override suspend fun getRecipes(): List<Recipe> {
        val recipesDto = ApiBook.service
            .getRecipes()
        val list : List<RecipeDto> = ArrayList<RecipeDto>(recipesDto.values)
        var i = 0
        
        recipesDto.keys.forEach { key ->
            list[i].id = key
            i++
        }
        return list.map { it.toDomainRecipe() }
    }

    override suspend fun postRecipe(recipe: Recipe) =
        ApiBook.service
            .postRecipe(recipe.toServerRecipe()).nodeId

    override suspend fun getUsers(): Map<String, User> =
        ApiBook.service
            .getUsers().mapValues {
                it.value.toDomainUser()
            }

    override suspend fun patchUser(uid: String, user: User): String? =
        ApiBook.service.patchUser(uid, user.toServerUser()).nodeId

    override suspend fun deleteUser(uid: String) {
        ApiBook.service
            .deleteUser(uid)
    }

    override suspend fun findUserById(uid: String): User =
        ApiBook.service.findUserById(uid).toDomainUser()
}