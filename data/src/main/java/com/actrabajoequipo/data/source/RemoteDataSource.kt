package com.actrabajoequipo.data.source

import com.actrabajoequipo.domain.Recipe
import com.actrabajoequipo.domain.User

interface RemoteDataSource {
    suspend fun getRecipes(): List<Recipe>
    suspend fun postRecipe(recipe: Recipe)
    suspend fun getUsers(): Map<String, User>
    suspend fun patchUser(uid: String, user: User)
    suspend fun editUser(uid: String, newUser: User)
    suspend fun deleteUser(uid: String)
    suspend fun postUser(user: User)
    suspend fun putUser(user: User)
}