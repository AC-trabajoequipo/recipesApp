package com.actrabajoequipo.usecases

import com.actrabajoequipo.data.repository.UserRepository
import com.actrabajoequipo.domain.User

class PatchUserUseCase(private val userRepository: UserRepository) {
    suspend fun invoke(uid: String, user: User) = userRepository.patchUser(uid, user)
}