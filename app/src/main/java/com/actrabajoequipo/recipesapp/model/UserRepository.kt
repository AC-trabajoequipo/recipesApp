package com.actrabajoequipo.recipesapp.model

class UserRepository {

    suspend fun postUser(user: UserDto) =
        UserBook.service
            .postUser(user)
}