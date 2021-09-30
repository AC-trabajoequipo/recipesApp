package com.actrabajoequipo.recipesapp.model.user

import com.actrabajoequipo.recipesapp.model.ApiBook

class UserRepository {

    suspend fun postUser(user: UserDto) =
        ApiBook.SERVICE
            .postUser(user)
}