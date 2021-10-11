package com.actrabajoequipo.recipesapp.model.user

import com.actrabajoequipo.recipesapp.model.ApiBook

class UserRepository {

    suspend fun postUser(user: UserDto) =
        ApiBook.SERVICE
            .postUser(user)

    suspend fun putUser(user: UserDto) =
        ApiBook.SERVICE
            .putUser(user)

    suspend fun patchUser(user: UserDto) =
        ApiBook.SERVICE
            .patchUser(user)

    suspend fun postUser2(user: UserDto) =
        ApiBook.SERVICE
            .postUser2(user)
}