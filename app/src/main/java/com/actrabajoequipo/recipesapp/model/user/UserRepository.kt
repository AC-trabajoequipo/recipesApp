package com.actrabajoequipo.recipesapp.model.user

import com.actrabajoequipo.recipesapp.model.ApiBook
import com.google.gson.JsonObject
import org.json.JSONObject

class UserRepository {

    suspend fun getUsers() :Map<String, UserDto> =
        ApiBook.service
            .getUsers()




    suspend fun patchUser(uid :String,user: UserDto) =
        ApiBook.service
            .patchUser(uid, user)

    suspend fun editUsername(uid: String, newUsername :UserDto) =
        ApiBook.service
            .editUsername(uid, newUsername)

    suspend fun editEmail(uid: String, newEmail :UserDto) =
        ApiBook.service
            .editEmail(uid, newEmail)

    suspend fun deleteUser(uid: String) =
        ApiBook.service
            .deleteUser(uid)


    //No se usan de momento
    suspend fun postUser(user: UserDto) =
        ApiBook.service
            .postUser(user)

    suspend fun putUser(user: UserDto) =
        ApiBook.service
            .putUser(user)

}