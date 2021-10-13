package com.actrabajoequipo.recipesapp.model.user

import com.actrabajoequipo.recipesapp.model.ApiBook
import com.google.gson.JsonObject
import org.json.JSONObject

class UserRepository {

    suspend fun getUsers() :List<UserDto>{
        val a = ApiBook.SERVICE
            .getUsers()
        return a
    }


    suspend fun postUser(user: UserDto) =
        ApiBook.SERVICE
            .postUser(user)

    suspend fun putUser(user: UserDto) =
        ApiBook.SERVICE
            .putUser(user)

    suspend fun patchUser(uid :String,user: UserDto) =
        ApiBook.SERVICE
            .patchUser(uid, user)
}