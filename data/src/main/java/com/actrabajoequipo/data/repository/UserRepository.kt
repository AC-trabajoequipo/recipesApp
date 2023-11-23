package com.actrabajoequipo.data.repository

import com.actrabajoequipo.data.source.RemoteDataSource
import com.actrabajoequipo.data.source.LocalDataSource
import com.actrabajoequipo.domain.User

class UserRepository(
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getUsers(): Map<String, User> =
        remoteDataSource.getUsers()

    suspend fun patchUser(uid: String, user: User) =
        remoteDataSource.patchUser(uid, user)

    suspend fun deleteUser(uid: String) =
        remoteDataSource.deleteUser(uid)

    suspend fun findUserById(uid: String): User? =
        remoteDataSource.findUserById(uid)
}