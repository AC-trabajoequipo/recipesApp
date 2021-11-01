package com.actrabajoequipo.data.repository

import com.actrabajoequipo.data.source.RemoteDataSource
import com.actrabajoequipo.domain.User

class UserRepository(
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getUsers(): Map<String, User> =
        remoteDataSource.getUsers()

    suspend fun patchUser(uid: String, user: User) =
        remoteDataSource.patchUser(uid, user)

    suspend fun editUser(uid: String, newUser: User) =
        remoteDataSource.editUser(uid, newUser)

    suspend fun deleteUser(uid: String) =
        remoteDataSource.deleteUser(uid)


    //No se usan de momento
    suspend fun postUser(user: User) =
        remoteDataSource.postUser(user)

    suspend fun putUser(user: User) =
        remoteDataSource.putUser(user)
}