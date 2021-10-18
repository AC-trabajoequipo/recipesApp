package com.actrabajoequipo.recipesapp.model.user

import com.actrabajoequipo.recipesapp.model.ApiBook
import com.google.gson.JsonObject
import org.json.JSONObject

class UserRepository {

    suspend fun getUsers() :Map<String, UserDto> =
        ApiBook.SERVICE
            .getUsers()



    suspend fun postUser(user: UserDto) =
        ApiBook.SERVICE
            .postUser(user)

    suspend fun putUser(user: UserDto) =
        ApiBook.SERVICE
            .putUser(user)

    suspend fun patchUser(uid :String,user: UserDto) =
        ApiBook.SERVICE
            .patchUser(uid, user)

    suspend fun editUsername(uid: String, newUsername :UserDto) =
        ApiBook.SERVICE
            .editUsername(uid, newUsername)

    suspend fun editEmail(uid: String, newEmail :UserDto) =
        ApiBook.SERVICE
            .editEmail(uid, newEmail)

    suspend fun deleteUser(uid: String) =
        ApiBook.SERVICE
            .deleteUser(uid)

}