package com.actrabajoequipo.data.source

import com.actrabajoequipo.domain.Recipe
import com.actrabajoequipo.domain.User

interface RemoteDataSource {
    suspend fun getRecipes(): List<Recipe>
    suspend fun postRecipe(recipe: Recipe): String?
    suspend fun getUsers(): Map<String, User>
    suspend fun patchUser(uid: String, user: User): String?
    suspend fun deleteUser(uid: String)
    suspend fun findUserById(uid: String): User
}