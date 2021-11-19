package com.actrabajoequipo.usecases

import com.actrabajoequipo.data.repository.UserRepository
import com.actrabajoequipo.domain.User

class FindUserByIdUseCase(private val userRepository: UserRepository) {
    suspend fun invoke(uid: String): User = userRepository.findUserById(uid)
}