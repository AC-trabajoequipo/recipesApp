package com.actrabajoequipo.usecases

import com.actrabajoequipo.data.repository.UserRepository
import com.actrabajoequipo.domain.User

class DeleteUserUseCase(private val userRepository: UserRepository) {
    suspend fun invoke(uid: String) = userRepository.deleteUser(uid)
}